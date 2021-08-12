package org.cxf.demo.restws.client;

import org.cxf.demo.restws.client.model.Patient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author olysenko
 */
public class PatientWSClient {

   private static final String PATIENT_SERVER_URL =
         "http://localhost:8080/restws/services/patientservice";

   private static final String PATIENTS_PATH = "/patients";

   public static void main(String[] args) {

      Client client = ClientBuilder.newClient();
      // Get patient
      WebTarget target = client.target(PATIENT_SERVER_URL).path(PATIENTS_PATH).path("/{id}")
            .resolveTemplate("id", 1L);
      Invocation.Builder request = target.request();
      Patient patient = request.get(Patient.class);
      System.out.println(patient);

      // Update patient
      patient.setName("Petro");

      WebTarget putTarget = client.target(PATIENT_SERVER_URL).path(PATIENTS_PATH);
      Response response =
            putTarget.request().put(Entity.entity(patient, MediaType.APPLICATION_XML_TYPE));
      System.out.println(response.getStatus());

      response.close();

      // Create new patient
      Patient newPatient = new Patient();
      newPatient.setName("Gena");

      WebTarget postTarget = client.target(PATIENT_SERVER_URL).path(PATIENTS_PATH);
      Patient createdPatient = postTarget.request()
            .post(Entity.entity(newPatient, MediaType.APPLICATION_XML_TYPE), Patient.class);

      System.out.println("Created patient: " + createdPatient);

      client.close();
   }

}
