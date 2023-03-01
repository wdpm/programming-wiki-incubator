struct ThreeInt {
    int a;
    int b;
    int c;
};

int main() {
    ThreeInt x{1, 2, 3}, y{6, 7, 8};
    ThreeInt *px = &x;
    (*px) = y;    // x: {6,7,8}
    (*px).a = 4;  // x: {4,7,8}
    px->b = 5;    // x: {4,5,8}
}
