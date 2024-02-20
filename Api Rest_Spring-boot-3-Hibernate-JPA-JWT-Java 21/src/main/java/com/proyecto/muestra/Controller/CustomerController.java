package com.proyecto.muestra.Controller;

import com.proyecto.muestra.Class.GenericObject;
import com.proyecto.muestra.Class.User;
import com.proyecto.muestra.DTOs.GenericObjectDTO;
import com.proyecto.muestra.DTOs.UserDTO;
import com.proyecto.muestra.Repository.GenericObjectRepository;
import com.proyecto.muestra.Repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("main")
@AllArgsConstructor
public class CustomerController {

    private final UserRepository userRepository;
    private final GenericObjectRepository genericObjectRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String welcome(){
        return "Hola reclutador mucho gusto soy Juan Carlos Rivera Preciado, bienvenido a mi aplicacion muestra realizada en spring";
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO userDTO){
        User user = User.builder()
                .userName(userDTO.userName())
                .password(passwordEncoder.encode(userDTO.password()))
                .email(userDTO.email())
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
    @PutMapping("/updateObjects")
    public ResponseEntity<GenericObject> updateObject(@RequestBody @Valid GenericObjectDTO genericObjectDTO){
        User user = userRepository.findByid(genericObjectDTO.userId());

        GenericObject genericObject = GenericObject.builder()
                .active(genericObjectDTO.active())
                .amount(genericObjectDTO.amount())
                .amountTotal(genericObjectDTO.amountTotal())
                .identification(genericObjectDTO.identification())
                .description(genericObjectDTO.description())
                .type(genericObjectDTO.type())
                .user(user)
                .build();

        user.setGenericObject(genericObject);

        genericObjectRepository.save(genericObject);

        return ResponseEntity.ok(genericObject);
    }

    @DeleteMapping("/deleteObject")
    public String deleteObject(@RequestParam @NotNull Long id){
         GenericObject genericObject = genericObjectRepository.findByid(id);
         genericObjectRepository.deleteById(genericObject.getId());
         return "El objecto con el numero de identificacion: ".concat(id.toString()).concat(" ha sido borrado.");
    }

    @PatchMapping("/updateObjectType")
    public ResponseEntity<GenericObject> updateData(@RequestParam char type, @RequestParam Long id){
        GenericObject genericObject = genericObjectRepository.findByid(id);
        genericObject.setType(type);

        genericObjectRepository.save(genericObject);

        return ResponseEntity.ok(genericObject);
    }

    //@PreAuthorize("hasRole('ADMINISTRADOR')")
    @RequestMapping(value = "/data/{idUser}/{typeObjects}", method = RequestMethod.GET)
    public ResponseEntity<List<GenericObject>> userObject(@PathVariable long idUser, @PathVariable char typeObjects){
        User user = userRepository.findByid(idUser);
        List<GenericObject> genericObjectList = user.getGenericObjects();

        List<GenericObject> objectsFound =  genericObjectList.stream().filter(genericObject ->  genericObject.getType() == typeObjects).toList();

        return ResponseEntity.ok(objectsFound);
    }
}
