package br.com.tecinfo;

import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

import java.io.File;
import java.util.UUID;

public class Deployments {

	public static WebArchive createInternalDeployment() {
		return ShrinkWrap.create(WebArchive.class, "internal"+ UUID.randomUUID().toString().substring(0,5) + ".war")
				.addPackages(true, "br.com.tecinfo")
				.deletePackages(true,"br.com.tecinfo.boundary")
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").addAsResource("import.sql")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	public static WebArchive createLoginScreenDeployment() {
		File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
                .withTransitivity().as(File.class);
		
		WebArchive webarchive = ShrinkWrap.create(WebArchive.class, "login.war")
                                .addPackages(true, "br.com.tecinfo")
				.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
						.importDirectory("src/main/webapp").as(GenericArchive.class), "/",
						Filters.include(".*\\.(jsf|xhtml|html|css|js|png|jpg|xml)$"))
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").addAsResource("import.sql")
				.addAsLibraries(libs)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.setWebXML(new File("src/main/webapp","WEB-INF/web.xml"))
				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml");
		System.out.println(webarchive.toString(true));		
		return webarchive;
	}

	public static WebArchive createScreenDeployment(String contextName) {
		File[] libs = Maven.resolver()
				.loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().as(File.class);

		WebArchive webarchive = ShrinkWrap.create(WebArchive.class, contextName + ".war")
				.addPackages(true, "br.com.tecinfo")
				.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
								.importDirectory("src/main/webapp").as(GenericArchive.class), "/",
						Filters.include(".*\\.(jsf|xhtml|html|css|js|png|jpg|xml)$"))
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").addAsResource("import.sql")
				.addAsLibraries(libs)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.setWebXML(new File("src/main/webapp","WEB-INF/web.xml"))
				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml");
		System.out.println(webarchive.toString(true));
		return webarchive;
	}
}
