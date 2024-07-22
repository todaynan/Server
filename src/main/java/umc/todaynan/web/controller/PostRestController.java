package umc.todaynan.web.controller;

import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.todaynan.apiPayload.ApiResponse;
import umc.todaynan.apiPayload.code.status.ErrorStatus;
import umc.todaynan.converter.PostConverter;
import umc.todaynan.domain.entity.Post.Post.Post;
import umc.todaynan.domain.entity.User.User.User;
import umc.todaynan.oauth2.TokenService;
import umc.todaynan.repository.UserRepository;
import umc.todaynan.service.PostService.PostQueryService;
import umc.todaynan.web.dto.PostDTO.PostResponseDTO;
import umc.todaynan.web.dto.UserDTO.UserResponseDTO;

import java.util.Optional;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostRestController {

    private final PostQueryService postQueryService;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Value("${JWT_SECRET_KEY}")
    private String secretKey;

    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getMiddleAddress(User user) {
        String address = user.getAddress();
        String[] parts = address.split(" ");
        return parts[1];
    }

    @GetMapping("/employ")
    @Operation(summary = "구인 게시판 전체 검색",description = "구인 게시판 게시들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page를 주세요")
    public ApiResponse<PostResponseDTO.PostListDTO> employPostList(
            HttpServletRequest request,
            @Parameter(description = "페이지 번호(1부터 시작), default: 1 / size = 10")
            @RequestParam(defaultValue = "1") Integer page
    ){
        String givenToken = tokenService.getJwtFromHeader(request);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            String middleAddress = getMiddleAddress(user.get());

            Page<Post> employPostPage = postQueryService.getEmployPostList(page-1, middleAddress);
            return ApiResponse.onSuccess(PostConverter.toPostListDTO(employPostPage));
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }
    }

    @GetMapping("/chat")
    @Operation(summary = "잡담 게시판 전체 검색",description = "잡담 게시판 게시들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page를 주세요")
    public ApiResponse<PostResponseDTO.PostListDTO> chatPostList(
            HttpServletRequest request,
            @Parameter(description = "페이지 번호(1부터 시작), default: 1 / size = 10")
            @RequestParam(defaultValue = "1") Integer page
    ){
        String givenToken = tokenService.getJwtFromHeader(request);
        String email = tokenService.getUid(givenToken);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            String middleAddress = getMiddleAddress(user.get());

            Page<Post> chatPostPage = postQueryService.getChatPostList(page-1, middleAddress);
            return ApiResponse.onSuccess(PostConverter.toPostListDTO(chatPostPage));
        }else {
            return ApiResponse.onFailure(ErrorStatus.USER_NOT_EXIST.getCode(), ErrorStatus.USER_NOT_EXIST.getMessage(), null);
        }
    }

    @GetMapping("/suggest")
    @Operation(summary = "추천 게시판 전체 검색",description = "추천 게시판 게시들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page와 검색 지역을 주세요")
    public ApiResponse<PostResponseDTO.PostListDTO> suggestPostList(
            @Parameter(description = "페이지 번호(1부터 시작), default: 1 / size = 10")
            @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "검색 지역, default: 전체")
            @RequestParam(defaultValue = "전체") String address
    ){
        String middleAddress = "";
        if(address.equals("전체")){
            middleAddress = "전체";
        } else {
            String[] parts = address.split(" ");
            middleAddress = parts[1];
        }

        Page<Post> suggestPostPage = postQueryService.getSuggestPostList(page-1, middleAddress);
        return ApiResponse.onSuccess(PostConverter.toPostListDTO(suggestPostPage));
    }

    @GetMapping("/hot")
    @Operation(summary = "HOT 게시판 전체 검색",description = "HOT 게시판 게시들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page을 주세요")
    public ApiResponse<PostResponseDTO.PostListDTO> hotPostList(
            @Parameter(description = "페이지 번호(1부터 시작), default: 1 / size = 10")
            @RequestParam(defaultValue = "1") Integer page
    ){

        Page<Post> hotPostPage = postQueryService.getHotPostList(page-1);
        return ApiResponse.onSuccess(PostConverter.toPostListDTO(hotPostPage));
    }
}
