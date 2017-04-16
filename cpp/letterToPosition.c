#include <stdio.h>
#include <ctype.h>

int main(){
	char l;
	printf("enter the letter :");
	scanf("%c", &l);
	int posititon = (int)toupper(l) - 64; 
	printf("position %d\n", posititon);
	return 0;
}