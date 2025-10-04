package fr.bred.example.interview.services;


import fr.bred.example.interview.models.City;
import fr.bred.example.interview.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements CityServiceInterface {
    private final CityRepository cityRepository;
    public CityService(CityRepository cityRepository)
    { this.cityRepository = cityRepository; }

    @Override
    public List<City> getCities(String namePattern, String zipCodePattern, Integer limit, Integer start, String sort, String order) {
        String nameRegex = wildcardToRegex(namePattern == null ? "*" : namePattern);
        String zipRegex = wildcardToRegex(zipCodePattern == null ? "*" : zipCodePattern);

        List<City> filtered = cityRepository.getCities().stream()
            .filter(c -> c.getName() != null && c.getName().toLowerCase().matches(nameRegex))
            .filter(c -> c.getZipCode() != null && c.getZipCode().toLowerCase().matches(zipRegex))
            .collect(java.util.stream.Collectors.toList());

        // Tri
        if (sort != null && (sort.equals("name") || sort.equals("zipCode"))) {
            java.util.Comparator<City> comparator = sort.equals("name") ?
                java.util.Comparator.comparing(c -> c.getName().toLowerCase()) :
                java.util.Comparator.comparing(c -> c.getZipCode().toLowerCase());
            if (order != null && order.equalsIgnoreCase("asc")) {
                filtered = filtered.stream().sorted(comparator).collect(java.util.stream.Collectors.toList());
            } else {
                filtered = filtered.stream().sorted(comparator.reversed()).collect(java.util.stream.Collectors.toList());
            }
        }

        // Pagination
        int safeStart = (start != null && start > 0) ? start : 0;
        int safeLimit = (limit != null && limit > 0) ? limit : filtered.size();
        return filtered.stream()
            .skip(safeStart)
            .limit(safeLimit)
            .collect(java.util.stream.Collectors.toList());
    }

    private String wildcardToRegex(String wildcard) {
        if (wildcard == null || wildcard.isEmpty()) return ".*";
        String s = wildcard.toLowerCase().replace(".", "\\.");
        s = s.replace("*", ".*");
        return "^" + s + "$";
    }
}
