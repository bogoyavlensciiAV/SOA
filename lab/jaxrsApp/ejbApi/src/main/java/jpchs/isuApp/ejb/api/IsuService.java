package jpchs.isuApp.ejb.api;

import jakarta.ejb.Remote;

@Remote
public interface IsuService {
    void moveStudents(long fromId, long toId);
    void changeEduForm(long groupId, String newForm);
}