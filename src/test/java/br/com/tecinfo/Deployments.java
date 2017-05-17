package br.com.tecinfo;

import java.util.UUID;

import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;

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
		WebArchive webarchive = ShrinkWrap.create(WebArchive.class, "login.war")
                                .addPackages(true, "br.com.tecinfo")
				.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
						.importDirectory("src/main/webapp").as(GenericArchive.class), "/",
						Filters.include(".*\\.xhtml$"))
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").addAsResource("import.sql")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")

				.addAsWebInfResource(new StringAsset("<faces-config version=\"2.0\"/>"), "faces-config.xml");
		return webarchive;
	}
}
