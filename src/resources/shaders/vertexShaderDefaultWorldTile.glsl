#version 330

uniform vec4 color;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

in vec4 in_Position;
in vec4 in_Color;
in vec2 in_mainTextureCoord;

in vec2 in_overlayTopLeftTextureCoord;
in vec2 in_overlayTopTextureCoord;
in vec2 in_overlayTopRightTextureCoord;
in vec2 in_overlayRightTextureCoord;
in vec2 in_overlayBottomRightTextureCoord;
in vec2 in_overlayBottomTextureCoord;
in vec2 in_overlayBottomLeftTextureCoord;
in vec2 in_overlayLeftTextureCoord;


out vec4 pass_Color;
out vec2 pass_mainTextureCoord;

out vec2 pass_overlayTopLeftTextureCoord;
out vec2 pass_overlayTopTextureCoord;
out vec2 pass_overlayTopRightTextureCoord;
out vec2 pass_overlayRightTextureCoord;
out vec2 pass_overlayBottomRightTextureCoord;
out vec2 pass_overlayBottomTextureCoord;
out vec2 pass_overlayBottomLeftTextureCoord;
out vec2 pass_overlayLeftTextureCoord;

void main(void) {
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * in_Position;

	pass_Color = in_Color;
	pass_mainTextureCoord = in_mainTextureCoord;

	pass_overlayTopLeftTextureCoord = in_overlayTopLeftTextureCoord;
    pass_overlayTopTextureCoord = in_overlayTopTextureCoord;
    pass_overlayTopRightTextureCoord = in_overlayTopRightTextureCoord;
    pass_overlayRightTextureCoord = in_overlayRightTextureCoord;
    pass_overlayBottomRightTextureCoord = in_overlayBottomRightTextureCoord;
    pass_overlayBottomTextureCoord = in_overlayBottomTextureCoord;
    pass_overlayBottomLeftTextureCoord = in_overlayBottomLeftTextureCoord;
    pass_overlayLeftTextureCoord = in_overlayLeftTextureCoord;
}