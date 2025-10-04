package fr.bred.example.interview.controllers;

import fr.bred.example.interview.models.City;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CityControllerInterface {

    @Operation(summary = "Liste toutes les villes")
    @GetMapping("/api/cities")
    List<City> getCities();

    @Operation(summary = "Recherche des villes par nom et/ou code postal",
               description = "Filtre les villes selon un pattern sur le nom et/ou le code postal. Les jokers '*' sont acceptés.")
    @GetMapping("/api/cities/search")
    List<City> searchCities(
            @Parameter(description = "Pattern du nom de la ville, joker '*' accepté", example = "MAR*")
            @RequestParam(required = false) String namePattern,
            @Parameter(description = "Pattern du code postal, joker '*' accepté", example = "1013*")
            @RequestParam(required = false) String zipCodePattern);
}

