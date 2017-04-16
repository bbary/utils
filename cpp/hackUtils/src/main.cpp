/*
 * main.cpp
 *
 *  Created on: Apr 15, 2017
 *      Author: brahim
 */

#include <iostream>
#include <string>
#include <list>
#include <math.h>
using namespace std;

bool checksum(string login, string dic) {
	string password = "souris";
	int nblog = login.size();
	int nbpass = password.size();
	int sum = 1;
	int n = max(nblog, nbpass);
	for (int i = 0; i < n; i++) {
		int index1;
		if(i < nblog)
			index1 = dic.find(login.at(i)) + 10;
		else
			index1 = 10;
		int index2 = dic.find(password.at(i)) + 10;
		sum = sum + (index1 * n * (i + 1)) * (index2 * (i + 1) * (i + 1));

		//cout << index1 << " " << index2 << " " << sum << endl;
	}
	if (sum == 3696619) {
		return true;
	} else
		return false;
}

string bruteForce(string dic, string word, int len, list<string> *words) {
	if (len == 0) {
		if (checksum(word, "                   azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDFGHJKLMWXCVBN0123456789_$&#@")) {
			cout << "pass is " << word << endl;
			(*words).push_back(word);
		}
		return "";
	} else {
		for (int i = 0; i < dic.size(); i++) {
			//word+=dic.at(i);
			bruteForce(dic, word + dic.at(i), len - 1, words);
			//cout << word << endl;
		}
		return word;
	}
}

int main() {
	cout << "brute force demo" << endl;
	int length = 6;
	string dic =
			"azertyuiopqsdfghjklmwxcvbn";
	cout << "generating " << pow(dic.size(), length) << " words" << endl;
	list<string> words;
	bruteForce(dic, "", length, &words);
	//checksum("azer", dic);
	cout << "found " << words.size() << " passes" << endl;

//	for (list<string>::iterator it = words.begin(); it != words.end(); ++it) {
//		if ((*it).find(' ') == string::npos) {
//			cout << "pass is " << *it << endl;
//		}
//	}
//	cout << "pass not found. Try with another word length " << endl;
	return 0;
}

