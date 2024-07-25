package yeomeong.common.repository.mongo;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import yeomeong.common.entity.mongo.MongoMemoPerTeacher;

@Repository
public interface MongoTeacherMemoRepository extends MongoRepository<MongoMemoPerTeacher, String>, MongoTeacherMemoRepositoryCustom{

}

// { teacher_id : "teacherId",
//    memos : [
//              {
//                  date : "date",
//                  memos_per_date : [
//                      { memo_id : "memoId",
//                        createdTime : "createdTime",
//                        updatedTime : "updatedTime",
//                        kids : [ kid1, kid2, ... , kinN ],
//                        tags : [ tag1, tag2, ... , tagK ],
//                        content : "content",
//                      },
//                      ...
//                      { memo_id : "memoId",
//                        createdTime : "createdTime",
//                        updatedTime : "updatedTime",
//                        kids : [ kid1, kid2, ... , kinN ],
//                        tags : [ tag1, tag2, ... , tagK ],
//                        content : "content",
//                      },
//                  ]
//              },
//              ...
//            },
//            ...
//    ]
// }
