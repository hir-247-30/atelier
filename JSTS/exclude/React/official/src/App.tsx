import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

const flg = true

interface MsgProps {
    msg      : string,
    fontSize : number,
    color    : string
}

function Msg(props: MsgProps) {
    const { fontSize, color } = props
    const style = {
        fontSize,
        color
    }
    return <p className='msg' style={ style }>{ props.msg }</p>
}

function ChildMsg(props: { children: string }) {
    return (
        <div className="child-msg">
            { props.children }
        </div>
    )
}

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.tsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
        <Msg msg={"テスト1"} fontSize={10} color={"red"}/>
        <Msg msg={"テスト2"} fontSize={15} color={"blue"}/>
        { flg ? <Msg msg={"テストTRUE"} fontSize={20} color={"white"}/> : <Msg msg={"テストFALSE"} fontSize={20} color={"white"}/> }
        <ChildMsg>
            子要素のメッセージ
        </ChildMsg>
    </>
  )
}

export default App
