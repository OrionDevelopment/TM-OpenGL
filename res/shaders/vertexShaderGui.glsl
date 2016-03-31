#version 330

uniform vec4 color;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

in vec4 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;

out vec4 pass_Color;
out vec2 pass_TextureCoord;

void main(void) {
	gl_Position = viewMatrix * modelMatrix * in_Position;

	pass_Color = color * in_Color;
	pass_TextureCoord = in_TextureCoord;
}