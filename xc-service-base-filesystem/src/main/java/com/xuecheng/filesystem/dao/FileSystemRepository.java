package com.xuecheng.filesystem.dao;

import com.xuecheng.framework.domain.filesystem.FileSystem;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author wu on 2020/2/13 0013
 */
public interface FileSystemRepository extends MongoRepository<FileSystem, String> {
}
