package com.zte.oespaas.cep.rules.resources;


import com.zte.oespaas.cep.common.model.KafkaUrl;
import com.zte.oespaas.cep.common.model.ResultMessage;
import com.zte.oespaas.cep.common.model.RulesMessage;
import com.zte.oespaas.cep.rules.api.CepRulesConfig;
import com.zte.oespaas.cep.rules.exception.TransactionException;
import com.zte.oespaas.cep.rules.service.RulesService;
import com.zte.oespaas.cep.rules.service.impl.EventsAcceptor;
import io.dropwizard.setup.Environment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cep")
//@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Api(tags = {"OES CEP Rules Resouce API"})
@Service
public class CepRulesResource {

    private static Logger logger = LoggerFactory.getLogger(CepRulesResource.class.getName());
    @Inject
    private RulesService rulesService;

    @Inject
    private Environment env;

    @Inject
    private CepRulesConfig configuration;

    @Inject
    private EventsAcceptor eventsAcceptor;

    @POST
    @Path("config/kafka")
    public Response configKafka(KafkaUrl url) {
        eventsAcceptor.modifyReceiver(url.getSrcTopic(), url.getSrcUrl());
        rulesService.modifySenders(url.getDstUrl(), url.getDstTopic());

        return Response.ok(url).build();
    }

    @GET
    @Path("getrules")
    @ApiOperation(value = "get rules", response = RulesMessage.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "get rules successfully.")})
    public Response getAllRules(@QueryParam("tenantId") String tenantId, @QueryParam("appId") String appId) {
        return Response.ok(rulesService.getAllRules(tenantId, appId)).build();
    }

    @POST
    @Path("addrules")
    public Response addRuels(RulesMessage rulesMessage) {
        try {
            rulesService.addRules(rulesMessage);
        } catch (TransactionException e) {
            logger.error(e.getMessage());
            ResultMessage resultMessage = new ResultMessage("ERROR", "This Rule has existed，Please do not add repeatedly!");
            return response(resultMessage, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        }
        return response(new ResultMessage("OK", "Add rules successfully"), Response.Status.OK.getStatusCode());
    }

    @POST
    @Path("updaterules")
    public Response updateRules(RulesMessage rulesMessage) {
        rulesService.updateRules(rulesMessage);
        return response(new ResultMessage("OK", "Update rules successfully"), Response.Status.OK.getStatusCode());
    }

    //TODO 当删除不存在的条件时，是否应该返回一些提示。
    @DELETE
    @Path("delrules/app")
    public Response deleteRules(@QueryParam("tenantId") String tenantId, @QueryParam("appId") String appId) {
        rulesService.deleteRules(tenantId, appId);
        return Response.noContent().build();
    }

    //TODO 当删除不存在的条件时，是否应该返回一些提示。
    @DELETE
    @Path("delrules/app/ruletype")
    public Response deleteRules(@QueryParam("tenantId") String tenantId, @QueryParam("appId") String appId, @QueryParam("ruleType") String ruleType) {
        rulesService.deleteRules(tenantId, appId, ruleType);
        return Response.noContent().build();
    }

    private Response response(Object entity, int status) {
        return Response.status(status).entity(entity)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE, HEAD")
                .header("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept")
                .build();
    }

}