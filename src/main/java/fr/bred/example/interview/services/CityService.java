package fr.bred.example.interview.services;


import fr.bred.example.interview.models.City;
import fr.bred.example.interview.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService implements CityServiceInterface {
    private final CityRepository cityRepository;
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Recherche et retourne la liste des villes selon plusieurs critères : filtrage, tri et pagination.
     *
     * @param namePattern Wildcard pattern pour filtrer par nom (insensible à la casse, joker '*', défaut '*')
     * @param zipCodePattern Wildcard pattern pour filtrer par code postal (insensible à la casse, joker '*', défaut '*')
     * @param limit Taille maximale du résultat (défaut : pas de limite, doit être positif si défini)
     * @param start Index du premier résultat (défaut : 0, doit être positif si défini)
     * @param sort Propriété de tri ("name" ou "zipCode", défaut : aucun tri)
     * @param order Sens du tri ("asc" ou "desc", défaut : "desc")
     * @return Liste filtrée, paginée et triée de villes
     */
    @Override
    public List<City> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start, String sort, String order) {
        // Filtrage par nom et code postal
        String nameRegex = wildcardToRegex(namePattern);
        String zipRegex = wildcardToRegex(zipCodePattern);
        List<City> filtered = cityRepository.getCities().stream()
            .filter(c -> matchesPattern(c.getName(), nameRegex))
            .filter(c -> matchesPattern(c.getZipCode(), zipRegex))
            .collect(Collectors.toList());

        // Tri
        filtered = sortCities(filtered, sort, order);

        // Pagination
        filtered = paginate(filtered, limit, start);

        return filtered;
    }

    /**
     * Vérifie si une valeur correspond au pattern regex (insensible à la casse).
     *
     * @param value Valeur à tester (nom ou code postal)
     * @param regex Expression régulière générée à partir du pattern
     * @return true si la valeur matche le pattern, false sinon
     */
    private boolean matchesPattern(String value, String regex) {
        return value != null && value.toLowerCase().matches(regex);
    }

    /**
     * Trie une liste de villes selon le critère et l'ordre spécifiés.
     *
     * @param cities Liste des villes à trier
     * @param sort Critère de tri ("name" ou "zipCode")
     * @param order Ordre de tri ("asc" ou "desc")
     * @return Liste triée des villes
     */
    private List<City> sortCities(List<City> cities, String sort, String order) {
        if (sort == null || (!sort.equals("name") && !sort.equals("zipCode"))) return cities;
        Comparator<City> comparator = sort.equals("name") ?
            Comparator.comparing(c -> c.getName() == null ? "" : c.getName().toLowerCase()) :
            Comparator.comparing(c -> c.getZipCode() == null ? "" : c.getZipCode().toLowerCase());
        if (order != null && order.equalsIgnoreCase("asc")) {
            return cities.stream().sorted(comparator).collect(Collectors.toList());
        } else {
            return cities.stream().sorted(comparator.reversed()).collect(Collectors.toList());
        }
    }

    /**
     * Paginer une liste de villes selon les paramètres _limit et _start.
     *
     * @param cities Liste des villes à paginer
     * @param limit Nombre maximum de résultats à retourner (si null ou <= 0, retourne tout)
     * @param start Index du premier résultat à retourner (si null ou <= 0, commence à 0)
     * @return Sous-liste paginée des villes
     */
    private List<City> paginate(List<City> cities, Integer limit, Integer start) {
        int safeStart = (start != null && start > 0) ? start : 0;
        int safeLimit = (limit != null && limit > 0) ? limit : cities.size();
        return cities.stream()
            .skip(safeStart)
            .limit(safeLimit)
            .collect(Collectors.toList());
    }

    /**
     * Convertit un pattern avec joker '*' en expression régulière Java (insensible à la casse).
     *
     * @param wildcard Pattern avec joker (ex : "PAR*")
     * @return Expression régulière compatible Java
     */
    private String wildcardToRegex(String wildcard) {
        if (wildcard == null || wildcard.isEmpty()) return ".*";
        String s = wildcard.toLowerCase().replace(".", "\\.");
        s = s.replace("*", ".*");
        return "^" + s + "$";
    }
}
