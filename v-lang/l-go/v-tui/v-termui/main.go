package main

import (
	"fmt"
	"time"

	"github.com/gizak/termui/v3"
	"github.com/gizak/termui/v3/widgets"
)

func main() {
	if err := termui.Init(); err != nil {
		panic(err)
	}
	defer termui.Close()

	// 创建一个文本小部件
	textWidget := widgets.NewParagraph()

	// 创建一个网格布局
	grid := termui.NewGrid()
	grid.SetRect(0, 0, 50, 10)
	grid.Set(termui.NewRow(1.0, textWidget))

	// 将网格布局添加到终端
	termui.Render(grid)

	// 启动 goroutine 循环刷新
	go func() {
		for {
			// 更新文本小部件的内容
			textWidget.Text = fmt.Sprintf("Current time: %s", time.Now().Format("15:04:05"))

			// 刷新界面
			termui.Render(grid)

			// 等待一段时间
			time.Sleep(time.Second)
		}
	}()

	// 按下 q 键退出程序
	termuiEvents := termui.PollEvents()
	for {
		select {
		case e := <-termuiEvents:
			if e.Type == termui.KeyboardEvent && e.ID == "q" {
				// 停止循环
				return
			}
		}
	}
}
