package io.github.wdpm.jdk16;

//import jdk.incubator.foreign.CLinker;
//import jdk.incubator.foreign.FunctionDescriptor;
//import jdk.incubator.foreign.LibraryLookup;
//
//import java.lang.invoke.MethodHandle;
//import java.lang.invoke.MethodType;
//import java.nio.file.Path;
//import java.util.Optional;

public class JEP389Example {

    public static void main(String[] args) throws Throwable {

        //Path path = Path.of("D:\\Code\\MyGithubProjects\\java-version-features\\src\\main\\java\\io\\github\\wdpm\\jdk16\\hello.so");
        //
        //LibraryLookup libraryLookup = LibraryLookup.ofPath(path);
        //
        //Optional<LibraryLookup.Symbol> optionalSymbol = libraryLookup.lookup("printHello");
        //if (optionalSymbol.isPresent()) {
        //
        //    LibraryLookup.Symbol symbol = optionalSymbol.get();
        //
        //    FunctionDescriptor functionDescriptor = FunctionDescriptor.ofVoid();
        //
        //    MethodType methodType = MethodType.methodType(Void.TYPE);
        //
        //    MethodHandle methodHandle = CLinker.getInstance().downcallHandle(
        //            symbol.address(),
        //            methodType,
        //            functionDescriptor);
        //    methodHandle.invokeExact();
        //
        //}

    }
}