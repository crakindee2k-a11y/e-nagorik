package com.enagorik.singleton;

import com.enagorik.model.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Singleton: one immutable, fixed-fee catalog shared by every portal —
 * citizens, officers and the mayor all see the exact same government
 * fee. Anti-corruption guarantee for FR-2.
 */
public final class ServiceCatalog
{
    private static final ServiceCatalog INSTANCE = new ServiceCatalog();

    private final List<Service> services = new ArrayList<>();

    private ServiceCatalog()
    {
        services.add(new Service(1, "Birth Certificate", "Civil", 200, 7, new String[] {"NID", "Photo"}));
        services.add(new Service(2, "Trade License", "Business", 1500, 14, new String[] {"NID", "Trade Docs"}));
        services.add(new Service(3, "Holding Tax", "Tax", 500, 5, new String[] {"NID", "Khatian"}));
        services.add(new Service(4, "Death Certificate", "Civil", 200, 7, new String[] {"NID", "Hospital Report"}));
        services.add(new Service(5, "Road Complaint", "Complaint", 0, 3, new String[] {"Photo Evidence"}));
    }

    public static ServiceCatalog getInstance()
    {
        return INSTANCE;
    }

    public List<Service> getAllServices()
    {
        return Collections.unmodifiableList(services);
    }

    public Optional<Service> findById(int serviceId)
    {
        return services.stream()
            .filter(s -> s.getServiceId() == serviceId)
            .findFirst();
    }
}