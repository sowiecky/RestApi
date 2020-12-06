package inz.restapiproject.service;

import inz.restapiproject.model.Users;
import inz.restapiproject.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    //Zwraca wszystkich uzytkownikow
    public List<Users> getUsers(){
        return usersRepository.findAll();
    }

    //Dodaje uzytkownika do bazy
    public void saveuser(Users n) {
        usersRepository.save(n);
    }

    //Sprawdza po login&passwd
    public Boolean findLoginAndPassword(String login, String password) {

        List<Users> a = usersRepository.findByLoginAndPassword(login, password);
        if (a.isEmpty()) {
            return false;
        } else {
            System.out.println("Result: " + a.get(0).toString());
            return true;
        }
    }

    //Sprawdza po email&passwd
    public boolean findEmailAndPassword(String email, String password) {

        List<Users> a = usersRepository.findByEmailAndPassword(email, password);

        if(a.isEmpty()){
            return false;
        }else{
            return true;
        }

    }

    //Sprawdza czy istnieje juz taki login||email
    public String findLoginAndEmail(String login, String email){
        List<Users> u = usersRepository.findByLogin(login);
        List<Users> e = usersRepository.findByEmail(email);

        if (e.isEmpty() & u.isEmpty()) {
            return "User_doesnt_exist";
        } else{
            if(e.isEmpty()){
                return "Username_exist";
            }
            return "Email_exist";
        }

    }

    public String findLogin(String login){
        List<Users> u = usersRepository.findByLogin(login);
        long tempID = u.get(0).getId();
        String finalId = String.valueOf(tempID);
        System.out.println(tempID);

        return finalId;

    }

}
