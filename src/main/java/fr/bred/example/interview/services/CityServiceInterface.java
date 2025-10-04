package fr.bred.example.interview.services;

import fr.bred.example.interview.models.City;
import java.util.List;

public interface CityServiceInterface {
    /**
     * Retourne toutes les villes disponibles.
     * @return liste des villes
     */
    List<City> getCities();

    /**
     * Recherche des villes par nom et/ou code postal.
     * Les jokers '*' sont acceptés.
     * @param namePattern pattern du nom de la ville (ex: "MAR*")
     * @param zipPattern pattern du code postal (ex: "1013*")
     * @return liste filtrée des villes
     */
    List<City> searchCities(String namePattern, String zipPattern);
}
