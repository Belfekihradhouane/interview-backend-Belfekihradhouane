package fr.bred.example.interview.controllers;

import fr.bred.example.interview.models.City;
import fr.bred.example.interview.models.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CityControllerInterface {

    @Operation(summary = "List all cities")
    @GetMapping("/api/cities")
    List<City> getCities(
            @Parameter(description = "Wildcard pattern to search by name, case insensitive, '*' allowed", example = "PAR*")
            @RequestParam(required = false) String namePattern,
            @Parameter(description = "Wildcard pattern to search by zipCode, case insensitive, '*' allowed", example = "75*")
            @RequestParam(required = false) String zipCodePattern,
            @Parameter(description = "Maximum number of results to return")
            @RequestParam(defaultValue = "100") Integer limit,
            @Parameter(description = "Index of the first result to return")
            @RequestParam(defaultValue = "0") Integer start,
            @Parameter(description = "Sort property")
            @RequestParam(defaultValue = "name") SortType sort,
            @Parameter(description = "Sort order, 'asc' for ascending, 'desc' for descending")
            @RequestParam(defaultValue = "asc") String order);

    @Operation(
        summary = "Search the nearest city to a given point",
        description = "GET /api/cities/nearest endpoint to retrieve the nearest city to the provided coordinates. Coordinates must be provided as strings."
    )
    @GetMapping("/api/cities/nearest")
    City findNearestCity(
        @Parameter(description = "x coordinate of the search point", required = true)
        @RequestParam String x,
        @Parameter(description = "y coordinate of the search point", required = true)
        @RequestParam String y
    );
}
