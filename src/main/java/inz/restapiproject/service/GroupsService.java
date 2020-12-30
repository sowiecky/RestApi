package inz.restapiproject.service;

import inz.restapiproject.model.Groups;
import inz.restapiproject.model.Lights;
import inz.restapiproject.repository.GroupsRepository;
import inz.restapiproject.repository.LightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsService {


    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    LightsRepository lightsRepository;

    //Zapisanie do defaultowej grupy nowego uzytkownika
    //(UsersController)
    public void saveDefaultGroup(Groups group) {
        groupsRepository.save(group);
    }

    //Dodanie grupy usera
    //(GroupsController)
    public void saveGroup(Groups group){
        groupsRepository.save(group);
    }


    //Szukanie i zwrot wszystkich grup po idUsera
    //(GroupsController)
    public List<Groups> findAllGroupsForUserId(long idUser){
        return groupsRepository.findAllGroupsForUserId(idUser);
    }


    //Szukanie i zwrot defaultowej grupy dla podanego idUsera
    //(LightsController)
    public long findIdDefaultGroup(String defaultName, long idUser){
        Groups defaultGroups = groupsRepository.findIdDefaultGroup(defaultName, idUser).get(0);
        return defaultGroups.getId();
    }


    //Szukanie i zwrot id dla podanego idUsera i nazwy grupy
    //Nie ma zastosowania, ale moze byc potrzebny
    public long findJustIdGroupForUserIdAndName(String name, long idUser){
        return groupsRepository.findJustIdGroupForUserIdAndName(name, idUser);
    }

    //Szukanie i zwrot idGrupy dla podanej nazwy i idUsera
    //(GroupsController)
    public long findIdSeekGroup(String name, long iduser) {
        return groupsRepository.findIdSeekGroup(name, iduser);
    }

    //Sprawdzenie czy grupa istnieje juz w BD
    //(GroupsController)
    public List<Groups> checkExistOfGroup(String name) {
        return groupsRepository.checkExistOfGroup(name);
    }

    public Groups getGroupById(long groupId) {
        return groupsRepository.getGroupById(groupId);
    }

    public void deleteGroup(Groups group) {
        groupsRepository.delete(group);
    }

    public List<Groups> findAllGroupsForUserIdWODefault(long idUser) {
        return groupsRepository.findAllGroupsForUserIdWODefault(idUser, "Wszystkie urzadzenia");
    }
}
