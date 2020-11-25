# Using Babel with TypeScript

## Question
> what is the right way to convert files from TypeScript to JavaScript?

- Is your build output mostly the same as your source input files? Use tsc
- Do you need a build pipeline with multiple potential outputs? Use babel for transpiling and tsc for type checking
