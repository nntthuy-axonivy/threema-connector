package ch.ivyteam.threema.mocks;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ch.ivyteam.ivy.rest.client.config.IvyDefaultJaxRsTemplates;
import io.swagger.v3.oas.annotations.Hidden;

@Path(ThreemaServiceMock.PATH_SUFFIX)
@PermitAll
@Hidden
@SuppressWarnings("all")
public class ThreemaServiceMock {

	static final String PATH_SUFFIX  = "mock";

	public static final String URI = "{"+IvyDefaultJaxRsTemplates.APP_URL+"}/api/"+PATH_SUFFIX;
	
	@GET
	@Path("/lookup/{a:phone|email}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getThreemaIdByPhone(@QueryParam("id") String id) {
		Response resp;
		String threemaId = "ECHOECHO";
		
		if(id.equals("validId")) {
			resp = Response.ok().entity(threemaId).build();
		}else {
			resp = Response.status(403).build();
		}

		return resp;
	}

	
	@GET
	@Path("lookup/pubkeys")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getPublicKey(@QueryParam("id") String id) {
		Response resp;
		String pubKey = "";
		
		if(id.equals("validId")) {
			resp = Response.ok().entity(pubKey).build();
		}else {
			resp = Response.status(404).build();
		}

		return resp;
	}
	
}
