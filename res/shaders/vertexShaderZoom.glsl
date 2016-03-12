#version 330

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;

out vec4 pass_Color;
out vec2 pass_TextureCoord;

void main(void) {
	// Override gl_Position with our new calculated position
	gl_Position = projectionMatrix * in_Position;
    //gl_Position = in_Position;


	pass_Color = in_Color;
	pass_TextureCoord = in_TextureCoord;
}