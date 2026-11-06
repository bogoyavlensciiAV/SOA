package jpchs.isuApp.ejb.impl;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jpchs.isuApp.client.StudyGroupClient;
import jpchs.isuApp.client.StudyGroupClientFactory;
import jpchs.isuApp.ejb.api.IsuService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Stateless
public class IsuServiceBean implements IsuService {

    @Inject
    StudyGroupClientFactory clientFactory;

    @Override
    public void moveStudents(long fromId, long toId) {
        System.out.println(1);
        StudyGroupClient client = clientFactory.create();
        JsonObject from = client.getById(fromId);
        JsonObject to = client.getById(toId);

        long moved = from.getJsonNumber("students-count").longValue();
        long targetStudents = to.getJsonNumber("students-count").longValue();

        JsonObject patchedTo = Json.createObjectBuilder(to)
                .add("students-count", targetStudents + moved)
                .build();

        JsonObject patchedFrom = Json.createObjectBuilder(from)
                .add("students-count", 0L)
                .build();
        System.out.println(4);

        client.update(toId, patchedTo);
        client.update(fromId, patchedFrom);
    }

    @Override
    public void changeEduForm(long groupId, String newForm) {
        System.out.println(2);
        StudyGroupClient client = clientFactory.create();
        JsonObject group = client.getById(groupId);
        System.out.println(3);

        JsonObject patchedGroup = Json.createObjectBuilder(group)
                .add("form-of-education", newForm)
                .build();

        client.update(groupId, patchedGroup);
    }
}