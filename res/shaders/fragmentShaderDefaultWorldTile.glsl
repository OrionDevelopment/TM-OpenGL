#version 330

uniform sampler2D texture_diffuse;

in vec4 pass_Color;
in vec2 pass_mainTextureCoord;

in vec2 pass_overlayTopLeftTextureCoord;
in vec2 pass_overlayTopTextureCoord;
in vec2 pass_overlayTopRightTextureCoord;
in vec2 pass_overlayRightTextureCoord;
in vec2 pass_overlayBottomRightTextureCoord;
in vec2 pass_overlayBottomTextureCoord;
in vec2 pass_overlayBottomLeftTextureCoord;
in vec2 pass_overlayLeftTextureCoord;

out vec4 out_Color;

void main(void) {
    vec4 overlayColor = vec4(0);

    if (!(pass_overlayTopLeftTextureCoord.x < 0 ||  pass_overlayTopLeftTextureCoord.y < 0)) {
         overlayColor = texture(texture_diffuse, pass_overlayTopLeftTextureCoord);
    } else if (!(pass_overlayTopRightTextureCoord.x < 0 ||  pass_overlayTopRightTextureCoord.y < 0)) {
         overlayColor = texture(texture_diffuse, pass_overlayTopRightTextureCoord);
    } else if (!(pass_overlayBottomRightTextureCoord.x < 0 ||  pass_overlayBottomRightTextureCoord.y < 0)) {
         overlayColor = texture(texture_diffuse, pass_overlayBottomRightTextureCoord);
    } else if (!(pass_overlayBottomLeftTextureCoord.x < 0 ||  pass_overlayBottomLeftTextureCoord.y < 0)) {
         overlayColor = texture(texture_diffuse, pass_overlayBottomLeftTextureCoord);
    } else if (!(pass_overlayTopTextureCoord.x < 0 ||  pass_overlayTopTextureCoord.y < 0)) {
         overlayColor = texture(texture_diffuse, pass_overlayTopTextureCoord);
    } else if (!(pass_overlayRightTextureCoord.x < 0 ||  pass_overlayRightTextureCoord.y < 0)) {
         overlayColor = texture(texture_diffuse, pass_overlayRightTextureCoord);
    } else if (!(pass_overlayBottomTextureCoord.x < 0 ||  pass_overlayBottomCoord.y < 0)) {
         overlayColor = texture(texture_diffuse, pass_overlayBottomTextureCoord);
    } else if (!(pass_overlayLeftTextureCoord.x < 0 ||  pass_overlayLeftTextureCoord.y < 0)) {
         overlayColor = texture(texture_diffuse, pass_overlayLeftTextureCoord);
    }

    out_Color = pass_Color * texture(texture_diffuse, pass_mainTextureCoord);

    if (overlayColor.a > 10) {
        out_Color = pass_Color * overlayColor;
    }
}
