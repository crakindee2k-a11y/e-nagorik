package com.enagorik.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Template Method: every repository needs the same read-file /
 * write-file skeleton; only "how to read an entity's ID" differs per
 * subclass. Removes what would otherwise be duplicated load/save code
 * in every concrete repository (DRY fix for Topic 3's Duplicated Code).
 */
public abstract class AbstractFileRepository<T extends Serializable, ID> implements Repository<T, ID>
{
    private final String filePath;
    protected List<T> cache;

    protected AbstractFileRepository(String filePath)
    {
        this.filePath = filePath;
        this.cache = load();
    }

    protected abstract ID extractId(T entity);

    @Override
    public void save(T entity)
    {
        cache.removeIf(existing -> extractId(existing).equals(extractId(entity)));
        cache.add(entity);
        persist();
    }

    @Override
    public Optional<T> findById(ID id)
    {
        return cache.stream()
            .filter(e -> extractId(e).equals(id))
            .findFirst();
    }

    @Override
    public List<T> findAll()
    {
        return new ArrayList<>(cache);
    }

    @SuppressWarnings("unchecked")
    private List<T> load()
    {
        File file = new File(filePath);

        if (!file.exists())
        {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
        {
            return (List<T>) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            return new ArrayList<>();
        }
    }

    private void persist()
    {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file)))
        {
            out.writeObject(cache);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not save to " + filePath, e);
        }
    }
}