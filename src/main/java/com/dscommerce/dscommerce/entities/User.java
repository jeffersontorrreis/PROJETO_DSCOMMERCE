package com.dscommerce.dscommerce.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;

    private String phone;
    private LocalDate birthDate;
    private String password;


    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tb_user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn (name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User(){

    }

    public User(long id, String password, LocalDate birthDate, String phone, String email, String name) {
        this.id = id;
        this.password = password;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*NUnca criar set de collection*/
    public List<Order> getOrders() {
        return orders;
    }



    public void addRole(Role role){
        roles.add(role); /*Adiciona o set<> no addRole*/
    }

    public boolean hasRole(String roleName){
        for (Role role : roles) {
            if (role.getAuthority().equals(roleName)){
                return true; /*Ainda não entendi, mas parece que existe uma comparação do roleName que entra no parensete como parametro e é comparado
                               com o Authority. Se for verdadeido retorna true, se for falso, sai do for e retorna false.*/
            }
        }
        return false;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
