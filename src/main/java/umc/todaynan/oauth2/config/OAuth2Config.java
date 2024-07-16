package umc.todaynan.oauth2.config;

public class OAuth2Config {

    public enum SocialType{
        GOOGLE("google"),
        KAKAO("kakao"),

        NAVER("naver");

        private final String socialName;
        SocialType(String socialName){
            this.socialName = socialName;
        }

        public String getSocialName(){
            return socialName;
        }
    }

}
