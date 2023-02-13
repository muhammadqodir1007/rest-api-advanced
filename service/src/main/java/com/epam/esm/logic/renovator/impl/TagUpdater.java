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
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Tag> updateListFromDatabase(List<Tag> newListOfTags) {
        List<Tag> tagsToPersist = new ArrayList<>();
        if (newListOfTags != null) {
            for (Tag tag : newListOfTags) {
                Optional<Tag> tagOptional = tagDao.findByName(tag.getName());
                if (tagOptional.isPresent()) {
                    tagsToPersist.add(tagOptional.get());
                } else {
                    tagsToPersist.add(tag);
                }
            }
        }
        return tagsToPersist;
    }
}