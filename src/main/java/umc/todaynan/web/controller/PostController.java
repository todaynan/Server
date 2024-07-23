package umc.todaynan.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.converter.PostConverter;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostLike.PostLike;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.service.PostService.PostCommandService;
import umc.todaynan.web.dto.PostDTO.PostRequestDTO;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostCommandService postCommandService;
    @Autowired
    private final TokenService tokenService;

    @Operation(summary = "게시글 작성 API",description = "게시판에 유저가 게시글을 작성하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @PostMapping("")
    public ApiResponse<PostResponseDTO.CreateResultDTO> createPost(@RequestBody PostRequestDTO.CreateDTO request,
                                                                   HttpServletRequest httpServletRequest){
        Post post = postCommandService.createPost(request, httpServletRequest);
        return ApiResponse.of(SuccessStatus.POST_CREATED, PostConverter.toCreateResultDTO(post));
    }

    @Operation(summary = "게시글 수정 API",description = "유저가 작성한 게시글을 수정하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "post_id", description = "게시글의 id, path variable 입니다")
    })
    @PatchMapping("/{post_id}")
    public ApiResponse<PostResponseDTO.UpdateResultDTO> updatePost(@PathVariable("post_id") Long post_id,
                                                                   @RequestBody PostRequestDTO.UpdateDTO request,
                                                                   HttpServletRequest httpServletRequest){
        Post post = postCommandService.updatePost(post_id, request, httpServletRequest);
        return ApiResponse.of(SuccessStatus.POST_UPDATED, PostConverter.toUpdateResultDTO(post));

    }

    @Operation(summary = "게시글 삭제 API",description = "게시판에 유저가 게시글을 작성하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "post_id", description = "게시글의 id, path variable 입니다")
    })
    @DeleteMapping("/{post_id}")
    public ApiResponse<String> deletePost(@PathVariable("post_id") Long post_id,
//                                          @RequestBody PostRequestDTO.DeleteDTO request,
                                          HttpServletRequest httpServletRequest){
        Boolean isSuccess = postCommandService.deletePost(post_id, httpServletRequest);
        if(isSuccess){
            return ApiResponse.of(SuccessStatus.POST_DELETED, "게시글이 성공적으로 삭제되었습니다");
        }
        return null;
    }

    @Operation(summary = "게시글 좋아요 API",description = "게시판에 유저가 게시글을 작성하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "post_id", description = "게시글의 id, path variable 입니다")
    })
    @PostMapping("/like/{post_id}")
    public ApiResponse<PostResponseDTO.LikeResultDTO> likePost(@PathVariable("post_id") Long post_id,
//                                                               @RequestBody PostRequestDTO.LikeDTO request,
                                                               HttpServletRequest httpServletRequest){
        PostLike postLike = postCommandService.likePost(post_id, httpServletRequest);
        return ApiResponse.of(SuccessStatus.POST_LIKE_SUCCESS, PostConverter.toLikeResultDTO(postLike));
    }

}


