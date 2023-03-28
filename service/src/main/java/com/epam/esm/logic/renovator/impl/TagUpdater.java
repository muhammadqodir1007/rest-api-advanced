package com.epam.esm.logic.renovator.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.logic.renovator.Updater;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TagUpdater implements Updater<Tag> {
    private final TagDao tagDao;

    public TagUpdater(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Tag updateObject(Tag newTag, Tag oldTag) {

        if (newTag.getName() != null) {
            oldTag.setName(newTag.getName());
        }
        return oldTag;

    }

    @Override
    public List<Tag> updateListFromDatabase(List<Tag> newListOfTags) {
        List<Tag> tagsToPersist = new ArrayList<>();
        if (newListOfTags == null) {
            return tagsToPersist;
        }

        for (Tag tag : newListOfTags) {
            Optional<Tag> tagOptional = tagDao.findByName(tag.getName());
            tagOptional.ifPresentOrElse(
                    tagsToPersist::add,
                    () -> tagsToPersist.add(tag)
            );
        }

        return tagsToPersist;
    }

}
