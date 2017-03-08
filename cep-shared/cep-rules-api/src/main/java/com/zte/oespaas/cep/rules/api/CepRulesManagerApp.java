package com.zte.oespaas.cep.rules.api;

import com.zte.ums.zenap.hk2.IOCApplication;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;


public class CepRulesManagerApp extends IOCApplication<CepRulesConfig> {

    private static final String CONFIGURATION_FILE = "ceprules.yml";

    public static void main(String[] args) throws Exception {

        //这种方式，要求example.yml放在classpath根目录下，在实际的使用过程中，可以通过参数直接传入这个文件的绝对路径，而不用通过这种方式来获取
//    	String configFile = CepRulesManagerApp.class.getClassLoader().getResource(CONFIGURATION_FILE).getFile();
//    	
//    	args = new String[]{"server",configFile};
        new CepRulesManagerApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<CepRulesConfig> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/api-doc", "/", "index.html"));
        super.initialize(bootstrap);
    }

    @Override
    public void run(CepRulesConfig configuration, Environment environment) throws Exception {

        environment.servlets().addFilter("crossOriginFilter", new CrossOriginFilter()).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
//		environment.jersey().register(new CepRulesResource());
        super.run(configuration, environment);

    }
}
