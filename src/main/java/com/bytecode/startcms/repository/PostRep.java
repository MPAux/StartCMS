package com.bytecode.startcms.repository;

import com.bytecode.startcms.model.Post;

public interface PostRep extends BaseRep<Post>{	
	public Post findOnSave(Post post);
}
