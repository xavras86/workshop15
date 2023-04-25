package pl.zajavka.buisness;

import pl.zajavka.domain.Opinion;

public interface OpinionRepository {
    Opinion create(Opinion opinion);
    void removeAll();
}
