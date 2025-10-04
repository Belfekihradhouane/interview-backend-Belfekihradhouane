package fr.bred.example.interview.services;

import fr.bred.example.interview.models.City;
import java.util.List;

public interface CityServiceInterface {
    /**
     * Recherche et retourne la liste des villes selon plusieurs critères.
     *
     * @param namePattern Wildcard pattern pour filtrer par nom (insensible à la casse, joker '*', défaut '*')
     * @param zipCodePattern Wildcard pattern pour filtrer par code postal (insensible à la casse, joker '*', défaut '*')
     * @param limit Taille maximale du résultat (défaut : pas de limite, doit être positif si défini)
     * @param start Index du premier résultat (défaut : 0, doit être positif si défini)
     * @param sort Propriété de tri ("name" ou "zipCode", défaut : aucun tri)
     * @param order Sens du tri ("asc" ou "desc", défaut : "desc")
     * @return Liste filtrée, paginée et triée de villes
     */
    List<City> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start, String sort, String order);
}
