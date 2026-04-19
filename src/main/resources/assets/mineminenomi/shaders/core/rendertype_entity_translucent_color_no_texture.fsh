#version 150

uniform vec4 ColorModulator;

in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 normal;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor * ColorModulator;
    color *= lightMapColor;
	if (color.a < 0.1) {
		discard;
	}
    fragColor = color;
}

