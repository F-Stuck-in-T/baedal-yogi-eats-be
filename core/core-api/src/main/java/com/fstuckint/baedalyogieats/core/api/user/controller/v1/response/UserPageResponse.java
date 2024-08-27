package com.fstuckint.baedalyogieats.core.api.user.controller.v1.response;

import com.fstuckint.baedalyogieats.core.api.user.domain.UserResult;
import java.util.List;

public record UserPageResponse(List<UserResult> list, boolean hasNext) {

}
