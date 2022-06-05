package apiChanging;

import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndPhaseEndProject {
	
	Response response;
	String BaseURI ="http://23.22.134.133:8088/employees";
	
	@Test
	public void test1() {
		
		
		
		response =GetMethodAll();
		Assert.assertEquals(response.getStatusCode(), 200);
		
		response = PostMethod("Imran", "MS", "20020", "imran@gmail.com"); 
		Assert.assertEquals(response.getStatusCode(), 201);
        JsonPath jpath = response.jsonPath(); 
		int empid=jpath.get("id");
		System.out.println("id- "+ empid);
		
		response = PutMethod(empid, "immu", "m"); 
		Assert.assertEquals(response.getStatusCode(), 200);
       jpath = response.jsonPath();
      Assert.assertEquals(jpath.get("firstName"), "immu");

        response = DeleteMethod(empid); 
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getBody().asString(), "");
	
		response = GetMethod(empid); 
		Assert.assertEquals(response.getStatusCode(), 400);
		jpath = response.jsonPath();
	      Assert.assertEquals(jpath.get("message"), "Entity Not Found");

		
	System.out.println(response.getStatusCode());
	}
	
	
	public Response GetMethodAll() {
	RestAssured.baseURI = BaseURI;
	
	RequestSpecification request = RestAssured.given();
	
	Response response = request.get();
	String ResponseBody = response.getBody().asString();
	
	System.out.println(ResponseBody);
	return response;
	
	}
	
	public Response PostMethod(String FirstName, String LastName, String Salary, String email) {
		
		RestAssured.baseURI = BaseURI;
		
		JSONObject jobj =new JSONObject();
		jobj.put("firstName", FirstName);
		jobj.put("lastName", LastName);
		jobj.put("salary", Salary);
		jobj.put("email", email);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
		
		.accept(ContentType.JSON)
		.body(jobj.toString())
		
		.post();
		
return response;
		
	}
	
public Response PutMethod(int Empid, String firstName, String lastName) {
		
		RestAssured.baseURI = BaseURI;
		
		JSONObject jobj =new JSONObject();
		jobj.put("firstName", firstName);
		jobj.put("lastName", lastName);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON)
		
		.accept(ContentType.JSON)
		.body(jobj.toString())
		
		.put("/"+Empid);
		
return response;
		
}

public Response DeleteMethod(int Empid) {
	
	RestAssured.baseURI = BaseURI;
	
	RequestSpecification request = RestAssured.given();
	Response response = request.delete("/"+Empid);
	
	
return response;
	
}

public Response GetMethod(int Empid) {
	
	RestAssured.baseURI = BaseURI;
	
	RequestSpecification request = RestAssured.given();
	Response response = request.get("/"+Empid);
	
	
return response;
}


}