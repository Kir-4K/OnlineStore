package by.itacademy.dao;

import by.itacademy.entity.News;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsDao implements BaseDao<Long, News> {

    private static final NewsDao INSTANCE = new NewsDao();

    public static NewsDao getInstance() {
        return INSTANCE;
    }
}
