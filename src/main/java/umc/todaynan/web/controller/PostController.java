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
import umc.todaynan.apiPayload.code.status.SuccessStatus;
import umc.todaynan.converter.PostCommentConverter;
import umc.todaynan.converter.PostConverter;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.Post.PostComment.PostComment;
import umc.todaynan.domain.entity.Post.PostCommentLike.PostCommentLike;
import umc.todaynan.domain.entity.Post.PostLike.PostLike;
import umc.todaynan.service.PostCommentService.PostCommentCommandService;
import umc.todaynan.service.PostService.PostCommandService;
import umc.todaynan.web.dto.PostCommentDTO.PostCommentRequestDTO;
import umc.todaynan.web.dto.PostCommentDTO.PostCommentResponseDTO;
import umc.todaynan.web.dto.PostDTO.PostRequestDTO;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private final PostCommandService postCommandService;
    @Autowired
    private final PostCommentCommandService postCommentCommandService;

    @Operation(summary = "게시글 작성 API",description = "게시판에 유저가 게시글을 작성하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST2005",description = "OK, 성공"),
    })
    @PostMapping("")
    public ApiResponse<PostResponseDTO.CreateResultDTO> createPost(@RequestBody PostRequestDTO.CreatePostDTO request,
                                                                   HttpServletRequest httpServletRequest){
        Post post = postCommandService.createPost(request, httpServletRequest);
        return ApiResponse.of(SuccessStatus.POST_CREATED, PostConverter.toCreateResultDTO(post));
    }

    @Operation(summary = "게시글 수정 API",description = "유저가 작성한 게시글을 수정하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST2006",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "post_id", description = "게시글의 id, path variable 입니다")
    })
    @PatchMapping("/{post_id}")
    public ApiResponse<PostResponseDTO.UpdateResultDTO> updatePost(@PathVariable("post_id") Long post_id,
                                                                   @RequestBody PostRequestDTO.UpdatePostDTO request,
                                                                   HttpServletRequest httpServletRequest){
        Post post = postCommandService.updatePost(post_id, request, httpServletRequest);
        return ApiResponse.of(SuccessStatus.POST_UPDATED, PostConverter.toUpdateResultDTO(post));

    }

    @Operation(summary = "게시글 삭제 API",description = "게시판에 유저가 게시글을 삭제하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST2007",description = "OK, 성공"),
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
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "POST2008",description = "OK, 성공"),
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

    @Operation(summary = "댓글 작성 API",description = "유저가 댓글을 작성하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT2004",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "post_id", description = "게시글의 id, path variable 입니다")
    })
    @PostMapping("/comment/{post_id}")
    public ApiResponse<PostCommentResponseDTO.CreateResultDTO> createPostComment(@PathVariable("post_id") Long post_id,
                                                                                 @RequestBody PostCommentRequestDTO.CreatePostCommentDTO request,
                                                                                 HttpServletRequest httpServletRequest){
        PostComment postComment = postCommentCommandService.createComment(post_id, request, httpServletRequest);
        return ApiResponse.of(SuccessStatus.POST_COMMENT_CREATED, PostCommentConverter.toCreateResultDTO(postComment));
//        return null;
    }

    @Operation(summary = "댓글 수정 API",description = "유저가 작성한 댓글을 수정하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT2005",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "post_id", description = "게시글의 id, path variable 입니다"),
            @Parameter(name = "comment_id", description = "댓글의 id, path variable 입니다")
    })
    @PatchMapping("/comment/{post_id}/{comment_id}")
    public ApiResponse<PostCommentResponseDTO.UpdateResultDTO> updatePostComment(@PathVariable("post_id") Long post_id,
                                                                                 @PathVariable("comment_id") Long comment_id,
                                                                                 @RequestBody PostCommentRequestDTO.UpdatePostCommentDTO request,
                                                                                 HttpServletRequest httpServletRequest){
        PostComment postComment = postCommentCommandService.updateComment(post_id, comment_id, request, httpServletRequest);
        return ApiResponse.of(SuccessStatus.POST_COMMENT_UPDATED, PostCommentConverter.toUpdateResultDTO(postComment));
//        return null;
    }

    @Operation(summary = "댓글 삭제 API",description = "유저가 댓글을 삭제하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT2006",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "post_id", description = "게시글의 id, path variable 입니다"),
            @Parameter(name = "comment_id", description = "댓글의 id, path variable 입니다")
    })
    @PostMapping("/comment/{post_id}/{comment_id}")
    public ApiResponse<String> deletePostComment(@PathVariable("post_id") Long post_id,
                                                 @PathVariable("comment_id") Long comment_id,
                                                 HttpServletRequest httpServletRequest){
        Boolean isSuccess = postCommentCommandService.deleteComment(post_id, comment_id, httpServletRequest);
        if(isSuccess){
            return ApiResponse.of(SuccessStatus.POST_COMMENT_DELETED, "댓글이 성공적으로 삭제되었습니다");
        }
        return null;
    }

    @Operation(summary = "댓글 좋아요 API",description = "유저가 댓글에 좋아요를 누르는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMENT2007",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "post_id", description = "게시글의 id, path variable 입니다"),
            @Parameter(name = "comment_id", description = "댓글의 id, path variable 입니다")
    })
    @DeleteMapping("/comment/like/{post_id}/{comment_id}")
    public ApiResponse<PostCommentResponseDTO.LikeResultDTO> likePostComment(@PathVariable("post_id") Long post_id,
                                                                             @PathVariable("comment_id") Long comment_id,
                                                                             HttpServletRequest httpServletRequest){
        PostCommentLike postCommentLike = postCommentCommandService.likeComment(post_id, comment_id, httpServletRequest);
        return ApiResponse.of(SuccessStatus.POST_COMMENT_LIKE_SUCCESS, PostCommentConverter.toLikeResultDTO(postCommentLike));
//        return null;
    }

}


