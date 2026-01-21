package main

import (
	"golang.org/x/tour/pic"
	"image"
	"image/color"
)

//ColorModel 应当返回 color.RGBAModel。
//Bounds 应当返回一个 image.Rectangle ，例如 image.Rect(0, 0, w, h)。
//At 应当返回一个颜色。上一个图片生成器的值 v 对应于此次的 color.RGBA{v, v, 255, 255}。
type Image struct{}

func (i Image) ColorModel() color.Model {
	return color.RGBAModel
}

func (i Image) Bounds() image.Rectangle {
	//hardcode for example
	return image.Rect(0, 0, 200, 200)
}

func (i Image) At(x, y int) color.Color {
	return color.RGBA{R: uint8(x), G: uint8(y), B: uint8(255), A: uint8(255)}
}

func main() {
	m := Image{}
	pic.ShowImage(m)
}
