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
    List<City> getCities(
            @Parameter(description = "Pattern du nom de la ville, joker '*' accepté", example = "MAR*")
            @RequestParam(required = false) String namePattern,
            @Parameter(description = "Pattern du code postal, joker '*' accepté", example = "1013*")
            @RequestParam(required = false) String zipCodePattern,
            @Parameter(description = "Nombre maximum de résultats à retourner")
            @RequestParam(defaultValue = "100") Integer limit,
            @Parameter(description = "Index du premier résultat à retourner")
            @RequestParam(defaultValue = "0") Integer start,
            @Parameter(description = "Critère de tri des résultats")
            @RequestParam(defaultValue = "name") String sort,
            @Parameter(description = "Ordre de tri des résultats, 'asc' pour croissant, 'desc' pour décroissant")
            @RequestParam(defaultValue = "asc") String order);
}
