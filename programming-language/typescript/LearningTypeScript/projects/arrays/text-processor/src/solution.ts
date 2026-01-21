export type AlignmentOptions = {
	align?: "left" | "middle" | "right";
	width: number;
};

export function alignTexts(
	texts: string[],
	options: AlignmentOptions
): string[][] {
	const alignedTextsLines: string[][] = [];

	for (const text of texts) {
		const lines = splitLines(text, options.width);
		const aligned = alignLines(lines, options);

		alignedTextsLines.push(aligned);
	}

	return alignedTextsLines;
}

function splitLines(text: string, width: number) {
	const lines: string[] = [];
	let line = "";

	// ["ab c d"], { width: 4 }
	// ab c
	// d

	for (const word of text.split(" ")) {
		if (line === "") {
			// 初始第一次进入
			line = word;
		} else if (line.length + word.length < width) {
			// 长度不够，暂时还不需要切分，那就继续合并
			line += ` ${word}`;
		} else {
			// 长度足够，将之前保存的line保存到数组，并重置当前line活跃变量
			lines.push(line);
			line = word;
		}
	}

	lines.push(line);

	return lines;
}

function alignLines(
	lines: string[],
	{ align = "left", width }: AlignmentOptions
) {
	const aligned: string[] = [];

	for (const line of lines) {
		const remainingSpaces = width - line.length;
		let newLine = line;

		if (remainingSpaces) {
			switch (align) {
				case "left":
					for (let i = 0; i < remainingSpaces; i += 1) {
						newLine += " ";
					}
					break;

				case "middle":
					// ceil，说明是偏向右侧的居中
					for (let i = 0; i < Math.ceil(remainingSpaces / 2); i += 1) {
						newLine += " ";
					}

					for (let i = 0; i < Math.floor(remainingSpaces / 2); i += 1) {
						newLine = " " + newLine;
					}

					break;

				case "right":
					for (let i = 0; i < remainingSpaces; i += 1) {
						newLine = " " + newLine;
					}
					break;
			}
		}

		aligned.push(newLine);
	}

	return aligned;
}
