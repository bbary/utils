#include <stdio.h>
#include <ctype.h>

int main(){
	int p;
	printf("enter the letter position [1-26]:");
	scanf("%d", &p);
	char letter = (char) p + 64;
	printf("Letter %c\n", letter);
	return 0;
}
