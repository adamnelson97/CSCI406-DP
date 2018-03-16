/*
 * Dynamic Programming Gas Station Problem
 * Authors: Adam Nelson, Akshay Swaminathan, Lewis Setter
 * Date: 2018-03-16
 */

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
using namespace std;

/*
 * Function Prototypes
 */



int main() {
	//Read n, L, P, c from input file
	string filename;
	cout << "Enter the name of the input file (no .txt): ";
	cin >> filename;
	filename += ".txt";

	ifstream input(filename.c_str()); //Open the filestream
	if (!input.is_open()) { //Test that the file opened properly
		cerr << "Unable to open file " << filename << endl;
		return -1;
	}

	int n, L, P, c;
	input >> n;
	input >> L;
	input >> P;
	input >> c;

	/*
	 * Store the gas requirements for each day. Since the number of days may
	 * change with inputs, we cannot use an array since their size is static.
	 */





	return 0;
}


/*
 * Functions
 */

