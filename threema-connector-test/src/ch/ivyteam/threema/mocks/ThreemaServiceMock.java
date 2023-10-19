package ch.ivyteam.threema.mocks;

import javax.annotation.security.PermitAll;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.ivy.rest.client.config.IvyDefaultJaxRsTemplates;
import io.swagger.v3.oas.annotations.Hidden;

@Path(ThreemaServiceMock.PATH_SUFFIX)
@PermitAll
@Hidden
@SuppressWarnings("all")
public class ThreemaServiceMock {

	static final String PATH_SUFFIX  = "mock";
	private static final String THREEMA_ID = "validId";

	public static final String URI = "{"+IvyDefaultJaxRsTemplates.APP_URL+"}/api/"+PATH_SUFFIX;
	// {ivy.app.baseurl}/api/mock
	// https://msgapi.threema.ch
	
	@GET
	@Path("/lookup/{type}/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getThreemaIdByMail(@PathParam("type") String type, @PathParam("id") String id) {
		Response resp;
		if(id.equals("validId")) {
			resp = Response.ok().entity(THREEMA_ID).build();
		}else {
			resp = Response.status(404).build();
		}
		return resp;
	}	
	
	@GET
	@Path("/pubkeys/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getPublicKey(@PathParam("id") String id) {
		Response resp;
		String pubKey = "ffbb40cfced42f75c4d83c7d35300c0698bf3ef1ab49ace323a1bbc38ee23f36";
		if(id.equals(THREEMA_ID)) {
			resp = Response.ok().entity(pubKey).build();
		}else {
			resp = Response.status(404).build();
		}

		return resp;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/send_e2e")
	@Produces(MediaType.TEXT_PLAIN)
	public Response sendMessage(
			@FormParam("from") String from, 
			@FormParam("box") String box, 
			@FormParam("to") String to,
			@FormParam("secret") String secret,
			@FormParam("nonce") String nonce
			) {
		String msgId = "b2885aa81e9b9c93";
		Response resp;
		if(to.equals("validId")) {
			resp = Response.ok().entity(msgId).build();
		}else {
			resp = Response.status(404).build();
		}
		
		return resp;
	}
}
