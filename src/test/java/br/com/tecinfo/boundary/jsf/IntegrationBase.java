package br.com.tecinfo.boundary.jsf;

import br.com.tecinfo.Deployments;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

/**
 * Created by marruda on 22/06/2017.
 */
@RunWith(Arquillian.class)
public abstract class IntegrationBase extends BaseIT{

    @Deployment
    public static WebArchive createDeployment() {
        return Deployments.createScreenDeployment("integration-test");
    }

}
