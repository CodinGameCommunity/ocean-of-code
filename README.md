# Ocean of Code
Community contest for CodingGame, see [https://www.codingame.com/contests/ocean-of-code](https://www.codingame.com/contests/ocean-of-code)


## Run locally

### Prerequisites:

* JDK 1.8 (or higher).

* Maven 3.6.x.

### Follow the next steps respectively to run the game locally:

1. Run `mvn clean install`. This bundles the game dependencies and tests in one jar file inside the target directory.

1. Run `java -jar ./target/captainsonar-1.0-SNAPSHOT-fat-tests.jar "bot-1 exec command" "bot-2 exec command"`.

    > Note: The executable jar expects two cli arguments, which are the two exec commands for the first and second player bots. If a cli argument is missing, the DummyPlayer bot `src/test/java/DummyPlayer.java` will be used as a replacement.

    Here is an example on how to run two java bots against each:
    ```bash
    java -jar ./target/captainsonar-1.0-SNAPSHOT-fat-tests.jar "java -cp target/test-classes/ DummyPlayer" "java -cp target/test-classes/ DummyPlayer"
    ```

### Output:

Running the executable jar will output two things:

* A full dump of the game summary to the stdout.

* A web page to see the game in action (e.g. http://localhost:8888/test.html).

<details>
<summary>Click to expand a sample game summary output.</summary>

```
    WARNING: sun.reflect.Reflection.getCallerClass is not supported. This will impact performance.
    WARNING: An illegal reflective access operation has occurred
    WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.core.$ReflectUtils$2
    WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
    WARNING: All illegal access operations will be denied in a future release
    [2020/03/30 20:21:01][GF] INFO  : GameManager - Init
    1337
    [2020/03/30 20:21:01][GF] INFO  : GameManager - Turn 1
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[VIEW] 2]
    KEY_FRAME 0
    {"global":{"entitymodule":{"width":1920,"height":1080}},"frame":{"entitymodule":"CS;R;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;S;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;R;T;T;C;C;C;C;C;C;T;S;R;T;C;C;C;C;C;C;T;S;R\nU6 0 bw 60 i island/11x0x0x1.png v 1 x 630 y 210 bh 60;16 0 bw 60 i island/11111111.png v 1 x 510 y 870 bh 60;41 0 bw 60 i island/x11111x0.png v 1 x 1050 y 930 bh 60;238 0 v 1 x 165 w 205 y 45 F 0 c -1 W 10 z 1 h 205;46 0 bw 60 i island/x0x0x111.png v 1 x 1110 y 810 bh 60;243 0 f -2 v 1 a 0.8 x 1480 y 115 c 0 R 12 W 4;5 0 bw 60 i island/11111111.png v 1 x 510 y 210 bh 60;26 0 bw 60 i island/11111111.png v 1 x 570 y 930 bh 60;45 0 bw 60 i island/x0x0x111.png v 1 x 810 y 450 bh 60;244 0 f -2 v 1 a 0.8 x 1480 y 75 c 0 R 12 W 4;21 0 bw 60 i island/x0x111x0.png v 1 x 1110 y 270 bh 60;37 0 bw 60 i island/11x0x111.png v 1 x 1110 y 870 bh 60;38 0 bw 60 i island/x11111x0.png v 1 x 750 y 510 bh 60;47 0 bw 60 i island/11x0x0x1.png v 1 x 1290 y 870 bh 60;32 0 bw 60 i island/11x0x111.png v 1 x 810 y 510 bh 60;236 0 f -1 v 1 ff Arial ax 0.5 x 270 y 270 s 60 T $0 z 20 maxWidth 400;240 0 f -2 v 1 a 0.8 x 1480 y 235 c 0 R 12 W 4;22 0 bw 60 i island/x0x11111.png v 1 x 1170 y 270 bh 60;231 0 f -1 v 1 a 0.8 x 440 y 195 c 0 R 12 W 4;42 0 bw 60 i island/1111x0x1.png v 1 x 510 y 270 bh 60;242 0 f -2 v 1 a 0.8 x 1480 y 155 c 0 R 12 W 4;246 0 f -2 v 1 ff Arial ax 0.5 x 1650 y 270 s 60 T $1 z 20 maxWidth 400;19 0 bw 60 i island/x0x11111.png v 1 x 510 y 810 bh 60;237 0 bw 200 i $0 v 1 ax 0.5 x 270 y 50 bh 200;39 0 bw 60 i island/11x0x0x1.png v 1 x 810 y 570 bh 60;35 0 bw 60 i island/11x0x111.png v 1 x 1110 y 930 bh 60;28 0 bw 60 i island/x0x11111.png v 1 x 570 y 810 bh 60;48 0 bw 60 i island/x111x0x0.png v 1 x 1230 y 870 bh 60;30 0 bw 60 i island/11111111.png v 1 x 570 y 90 bh 60;43 0 bw 60 i island/x0x111x0.png v 1 x 750 y 450 bh 60;31 0 bw 60 i island/11110111.png v 1 x 570 y 210 bh 60;25 0 bw 60 i island/x0x0x111.png v 1 x 630 y 570 bh 60;233 0 f -1 v 1 a 0.8 x 440 y 115 c 0 R 12 W 4;40 0 bw 60 i island/x11111x0.png v 1 x 1050 y 870 bh 60;20 0 bw 60 i island/x0x0x111.png v 1 x 630 y 810 bh 60;29 0 bw 60 i island/x111x0x0.png v 1 x 570 y 630 bh 60;8 0 bw 60 i island/11111111.png v 1 x 510 y 150 bh 60;15 0 bw 60 i island/1111x0x1.png v 1 x 1170 y 330 bh 60;245 0 f -2 v 1 a 0.8 x 1480 y 35 c 0 R 12 W 4;18 0 bw 60 i island/11x0x0x1.png v 1 x 1230 y 330 bh 60;12 0 bw 60 i island/x111x0x0.png v 1 x 1110 y 330 bh 60;34 0 bw 60 i island/x0x111x0.png v 1 x 1230 y 810 bh 60;3 0 bw 60 i island/11111111.png v 1 x 510 y 90 bh 60;23 0 bw 60 i island/11x0x0x1.png v 1 x 630 y 630 bh 60;24 0 bw 60 i island/11111111.png v 1 x 570 y 150 bh 60;27 0 bw 60 i island/11111111.png v 1 x 570 y 870 bh 60;11 0 bw 60 i island/x0x111x0.png v 1 x 570 y 570 bh 60;235 0 f -1 v 1 a 0.8 x 440 y 35 c 0 R 12 W 4;9 0 bw 60 i island/11x0x111.png v 1 x 630 y 150 bh 60;14 0 bw 60 i island/11x0x111.png v 1 x 630 y 930 bh 60;36 0 bw 60 i island/x0x0x111.png v 1 x 1290 y 810 bh 60;248 0 v 1 x 1550 w 205 y 45 F 0 c -2 W 10 z 1 h 205;33 0 bw 60 i island/x111x0x0.png v 1 x 750 y 570 bh 60;10 0 bw 60 i island/x0x0x111.png v 1 x 1230 y 270 bh 60;234 0 f -1 v 1 a 0.8 x 440 y 75 c 0 R 12 W 4;232 0 f -1 v 1 a 0.8 x 440 y 155 c 0 R 12 W 4;13 0 bw 60 i island/11111111.png v 1 x 510 y 930 bh 60;7 0 bw 60 i island/11x0x0x1.png v 1 x 570 y 270 bh 60;230 0 f -1 v 1 a 0.8 x 440 y 235 c 0 R 12 W 4;247 0 bw 200 i $1 v 1 ax 0.5 x 1650 y 50 bh 200;44 0 bw 60 i island/x0x111x0.png v 1 x 1050 y 810 bh 60;4 0 bw 60 i island/11x0x111.png v 1 x 630 y 90 bh 60;17 0 bw 60 i island/11x0x111.png v 1 x 630 y 870 bh 60;241 0 f -2 v 1 a 0.8 x 1480 y 195 c 0 R 12 W 4;106 1 v 1 a 0 x 810 w 60 y 810 h 60;167 1 v 1 a 0 x 1110 w 60 y 150 h 60;174 1 v 1 a 0 x 1110 w 60 y 690 h 60;192 1 v 1 a 0 x 1230 w 60 y 390 h 60;51 1 v 1 a 0 x 510 w 60 y 450 h 60;135 1 v 1 a 0 x 930 w 60 y 750 h 60;113 1 v 1 a 0 x 870 w 60 y 330 h 60;199 1 v 1 a 0 x 1230 w 60 y 930 h 60;82 1 v 1 a 0 x 690 w 60 y 810 h 60;119 1 v 1 a 0 x 870 w 60 y 690 h 60;84 1 v 1 a 0 x 690 w 60 y 930 h 60;147 1 v 1 a 0 x 990 w 60 y 570 h 60;77 1 v 1 a 0 x 690 w 60 y 510 h 60;217 1 v 1 a 0 x 1350 w 60 y 330 h 60;69 1 v 1 a 0 x 630 w 60 y 750 h 60;146 1 v 1 a 0 x 990 w 60 y 510 h 60;179 1 v 1 a 0 x 1170 w 60 y 390 h 60;162 1 v 1 a 0 x 1050 w 60 y 570 h 60;197 1 v 1 a 0 x 1230 w 60 y 690 h 60;76 1 v 1 a 0 x 690 w 60 y 450 h 60;194 1 v 1 a 0 x 1230 w 60 y 510 h 60;203 1 v 1 a 0 x 1290 w 60 y 270 h 60;151 1 v 1 a 0 x 990 w 60 y 810 h 60;177 1 v 1 a 0 x 1170 w 60 y 150 h 60;55 1 v 1 a 0 x 510 w 60 y 690 h 60;121 1 v 1 a 0 x 870 w 60 y 810 h 60;131 1 v 1 a 0 x 930 w 60 y 510 h 60;216 1 v 1 a 0 x 1350 w 60 y 270 h 60;226 1 v 1 a 0 x 1350 w 60 y 870 h 60;176 1 v 1 a 0 x 1170 w 60 y 90 h 60;185 1 v 1 a 0 x 1170 w 60 y 750 h 60;80 1 v 1 a 0 x 690 w 60 y 690 h 60;1 1 i background.jpg ay 0 v 1 ax 0;70 1 v 1 a 0 x 690 w 60 y 90 h 60;98 1 v 1 a 0 x 810 w 60 y 150 h 60;153 1 v 1 a 0 x 990 w 60 y 930 h 60;184 1 v 1 a 0 x 1170 w 60 y 690 h 60;189 1 v 1 a 0 x 1230 w 60 y 90 h 60;150 1 v 1 a 0 x 990 w 60 y 750 h 60;68 1 v 1 a 0 x 630 w 60 y 690 h 60;97 1 v 1 a 0 x 810 w 60 y 90 h 60;204 1 v 1 a 0 x 1290 w 60 y 330 h 60;218 1 v 1 a 0 x 1350 w 60 y 390 h 60;227 1 v 1 a 0 x 1350 w 60 y 930 h 60;90 1 v 1 a 0 x 750 w 60 y 390 h 60;114 1 v 1 a 0 x 870 w 60 y 390 h 60;93 1 v 1 a 0 x 750 w 60 y 750 h 60;206 1 v 1 a 0 x 1290 w 60 y 450 h 60;212 1 v 1 a 0 x 1290 w 60 y 930 h 60;67 1 v 1 a 0 x 630 w 60 y 510 h 60;50 1 v 1 a 0 x 510 w 60 y 390 h 60;120 1 v 1 a 0 x 870 w 60 y 750 h 60;133 1 v 1 a 0 x 930 w 60 y 630 h 60;56 1 v 1 a 0 x 510 w 60 y 750 h 60;163 1 v 1 a 0 x 1050 w 60 y 630 h 60;142 1 v 1 a 0 x 990 w 60 y 270 h 60;202 1 v 1 a 0 x 1290 w 60 y 210 h 60;96 1 v 1 a 0 x 750 w 60 y 930 h 60;222 1 v 1 a 0 x 1350 w 60 y 630 h 60;158 1 v 1 a 0 x 1050 w 60 y 330 h 60;125 1 v 1 a 0 x 930 w 60 y 150 h 60;138 1 v 1 a 0 x 930 w 60 y 930 h 60;73 1 v 1 a 0 x 690 w 60 y 270 h 60;165 1 v 1 a 0 x 1050 w 60 y 750 h 60;239 1 f 16777215 v 1 ff Arial ax 1 x 1890 y 370 s 30 T  maxWidth 400 z 100;155 1 v 1 a 0 x 1050 w 60 y 150 h 60;213 1 v 1 a 0 x 1350 w 60 y 90 h 60;186 1 v 1 a 0 x 1170 w 60 y 810 h 60;63 1 v 1 a 0 x 630 w 60 y 270 h 60;130 1 v 1 a 0 x 930 w 60 y 450 h 60;164 1 v 1 a 0 x 1050 w 60 y 690 h 60;59 1 v 1 a 0 x 570 w 60 y 450 h 60;74 1 v 1 a 0 x 690 w 60 y 330 h 60;161 1 v 1 a 0 x 1050 w 60 y 510 h 60;173 1 v 1 a 0 x 1110 w 60 y 630 h 60;228 1 ay 0.5 v 1 ff Arial ax 0.5 x 960 S 2 y 1040 s 40 T  sc 0;122 1 v 1 a 0 x 870 w 60 y 870 h 60;143 1 v 1 a 0 x 990 w 60 y 330 h 60;140 1 v 1 a 0 x 990 w 60 y 150 h 60;94 1 v 1 a 0 x 750 w 60 y 810 h 60;209 1 v 1 a 0 x 1290 w 60 y 630 h 60;104 1 v 1 a 0 x 810 w 60 y 690 h 60;107 1 v 1 a 0 x 810 w 60 y 870 h 60;198 1 v 1 a 0 x 1230 w 60 y 750 h 60;52 1 v 1 a 0 x 510 w 60 y 510 h 60;116 1 v 1 a 0 x 870 w 60 y 510 h 60;136 1 v 1 a 0 x 930 w 60 y 810 h 60;101 1 v 1 a 0 x 810 w 60 y 330 h 60;91 1 v 1 a 0 x 750 w 60 y 630 h 60;141 1 v 1 a 0 x 990 w 60 y 210 h 60;81 1 v 1 a 0 x 690 w 60 y 750 h 60;124 1 v 1 a 0 x 930 w 60 y 90 h 60;160 1 v 1 a 0 x 1050 w 60 y 450 h 60;175 1 v 1 a 0 x 1110 w 60 y 750 h 60;95 1 v 1 a 0 x 750 w 60 y 870 h 60;149 1 v 1 a 0 x 990 w 60 y 690 h 60;200 1 v 1 a 0 x 1290 w 60 y 90 h 60;103 1 v 1 a 0 x 810 w 60 y 630 h 60;134 1 v 1 a 0 x 930 w 60 y 690 h 60;110 1 v 1 a 0 x 870 w 60 y 150 h 60;183 1 v 1 a 0 x 1170 w 60 y 630 h 60;225 1 v 1 a 0 x 1350 w 60 y 810 h 60;123 1 v 1 a 0 x 870 w 60 y 930 h 60;79 1 v 1 a 0 x 690 w 60 y 630 h 60;65 1 v 1 a 0 x 630 w 60 y 390 h 60;92 1 v 1 a 0 x 750 w 60 y 690 h 60;87 1 v 1 a 0 x 750 w 60 y 210 h 60;75 1 v 1 a 0 x 690 w 60 y 390 h 60;137 1 v 1 a 0 x 930 w 60 y 870 h 60;223 1 v 1 a 0 x 1350 w 60 y 690 h 60;62 1 v 1 a 0 x 570 w 60 y 750 h 60;182 1 v 1 a 0 x 1170 w 60 y 570 h 60;115 1 v 1 a 0 x 870 w 60 y 450 h 60;139 1 v 1 a 0 x 990 w 60 y 90 h 60;220 1 v 1 a 0 x 1350 w 60 y 510 h 60;159 1 v 1 a 0 x 1050 w 60 y 390 h 60;205 1 v 1 a 0 x 1290 w 60 y 390 h 60;89 1 v 1 a 0 x 750 w 60 y 330 h 60;60 1 v 1 a 0 x 570 w 60 y 510 h 60;219 1 v 1 a 0 x 1350 w 60 y 450 h 60;112 1 v 1 a 0 x 870 w 60 y 270 h 60;190 1 v 1 a 0 x 1230 w 60 y 150 h 60;214 1 v 1 a 0 x 1350 w 60 y 150 h 60;144 1 v 1 a 0 x 990 w 60 y 390 h 60;53 1 v 1 a 0 x 510 w 60 y 570 h 60;168 1 v 1 a 0 x 1110 w 60 y 210 h 60;156 1 v 1 a 0 x 1050 w 60 y 210 h 60;57 1 v 1 a 0 x 570 w 60 y 330 h 60;191 1 v 1 a 0 x 1230 w 60 y 210 h 60;193 1 v 1 a 0 x 1230 w 60 y 450 h 60;211 1 v 1 a 0 x 1290 w 60 y 750 h 60;195 1 v 1 a 0 x 1230 w 60 y 570 h 60;169 1 v 1 a 0 x 1110 w 60 y 390 h 60;99 1 v 1 a 0 x 810 w 60 y 210 h 60;66 1 v 1 a 0 x 630 w 60 y 450 h 60;187 1 v 1 a 0 x 1170 w 60 y 870 h 60;201 1 v 1 a 0 x 1290 w 60 y 150 h 60;109 1 v 1 a 0 x 870 w 60 y 90 h 60;72 1 v 1 a 0 x 690 w 60 y 210 h 60;64 1 v 1 a 0 x 630 w 60 y 330 h 60;215 1 v 1 a 0 x 1350 w 60 y 210 h 60;166 1 v 1 a 0 x 1110 w 60 y 90 h 60;88 1 v 1 a 0 x 750 w 60 y 270 h 60;224 1 v 1 a 0 x 1350 w 60 y 750 h 60;128 1 v 1 a 0 x 930 w 60 y 330 h 60;157 1 v 1 a 0 x 1050 w 60 y 270 h 60;196 1 v 1 a 0 x 1230 w 60 y 630 h 60;207 1 v 1 a 0 x 1290 w 60 y 510 h 60;181 1 v 1 a 0 x 1170 w 60 y 510 h 60;2 1 v 1 x 507 w 906 y 87 F 0 c 15724527 W 10 z 99 h 906;145 1 v 1 a 0 x 990 w 60 y 450 h 60;171 1 v 1 a 0 x 1110 w 60 y 510 h 60;229 1 f 16777215 v 1 ff Arial x 30 y 370 s 30 T  maxWidth 400 z 100;221 1 v 1 a 0 x 1350 w 60 y 570 h 60;100 1 v 1 a 0 x 810 w 60 y 270 h 60;105 1 v 1 a 0 x 810 w 60 y 750 h 60;108 1 v 1 a 0 x 810 w 60 y 930 h 60;54 1 v 1 a 0 x 510 w 60 y 630 h 60;129 1 v 1 a 0 x 930 w 60 y 390 h 60;170 1 v 1 a 0 x 1110 w 60 y 450 h 60;188 1 v 1 a 0 x 1170 w 60 y 930 h 60;71 1 v 1 a 0 x 690 w 60 y 150 h 60;178 1 v 1 a 0 x 1170 w 60 y 210 h 60;61 1 v 1 a 0 x 570 w 60 y 690 h 60;117 1 v 1 a 0 x 870 w 60 y 570 h 60;126 1 v 1 a 0 x 930 w 60 y 210 h 60;102 1 v 1 a 0 x 810 w 60 y 390 h 60;83 1 v 1 a 0 x 690 w 60 y 870 h 60;210 1 v 1 a 0 x 1290 w 60 y 690 h 60;152 1 v 1 a 0 x 990 w 60 y 870 h 60;127 1 v 1 a 0 x 930 w 60 y 270 h 60;154 1 v 1 a 0 x 1050 w 60 y 90 h 60;58 1 v 1 a 0 x 570 w 60 y 390 h 60;85 1 v 1 a 0 x 750 w 60 y 90 h 60;208 1 v 1 a 0 x 1290 w 60 y 570 h 60;172 1 v 1 a 0 x 1110 w 60 y 570 h 60;78 1 v 1 a 0 x 690 w 60 y 570 h 60;180 1 v 1 a 0 x 1170 w 60 y 450 h 60;148 1 v 1 a 0 x 990 w 60 y 630 h 60;86 1 v 1 a 0 x 750 w 60 y 150 h 60;118 1 v 1 a 0 x 870 w 60 y 630 h 60;49 1 v 1 a 0 x 510 w 60 y 330 h 60;111 1 v 1 a 0 x 870 w 60 y 210 h 60;132 1 v 1 a 0 x 930 w 60 y 570 h 60","tooltips":[{"3":"Island\nx = 0\ny = 0","4":"Island\nx = 2\ny = 0","5":"Island\nx = 0\ny = 2","6":"Island\nx = 2\ny = 2","7":"Island\nx = 1\ny = 3","8":"Island\nx = 0\ny = 1","9":"Island\nx = 2\ny = 1","10":"Island\nx = 12\ny = 3","11":"Island\nx = 1\ny = 8","12":"Island\nx = 10\ny = 4","13":"Island\nx = 0\ny = 14","14":"Island\nx = 2\ny = 14","15":"Island\nx = 11\ny = 4","16":"Island\nx = 0\ny = 13","17":"Island\nx = 2\ny = 13","18":"Island\nx = 12\ny = 4","19":"Island\nx = 0\ny = 12","20":"Island\nx = 2\ny = 12","21":"Island\nx = 10\ny = 3","22":"Island\nx = 11\ny = 3","23":"Island\nx = 2\ny = 9","24":"Island\nx = 1\ny = 1","25":"Island\nx = 2\ny = 8","26":"Island\nx = 1\ny = 14","27":"Island\nx = 1\ny = 13","28":"Island\nx = 1\ny = 12","29":"Island\nx = 1\ny = 9","30":"Island\nx = 1\ny = 0","31":"Island\nx = 1\ny = 2","32":"Island\nx = 5\ny = 7","33":"Island\nx = 4\ny = 8","34":"Island\nx = 12\ny = 12","35":"Island\nx = 10\ny = 14","36":"Island\nx = 13\ny = 12","37":"Island\nx = 10\ny = 13","38":"Island\nx = 4\ny = 7","39":"Island\nx = 5\ny = 8","40":"Island\nx = 9\ny = 13","41":"Island\nx = 9\ny = 14","42":"Island\nx = 0\ny = 3","43":"Island\nx = 4\ny = 6","44":"Island\nx = 9\ny = 12","45":"Island\nx = 5\ny = 6","46":"Island\nx = 10\ny = 12","47":"Island\nx = 13\ny = 13","48":"Island\nx = 12\ny = 13","49":"Water\nx = 0\ny = 4","50":"Water\nx = 0\ny = 5","51":"Water\nx = 0\ny = 6","52":"Water\nx = 0\ny = 7","53":"Water\nx = 0\ny = 8","54":"Water\nx = 0\ny = 9","55":"Water\nx = 0\ny = 10","56":"Water\nx = 0\ny = 11","57":"Water\nx = 1\ny = 4","58":"Water\nx = 1\ny = 5","59":"Water\nx = 1\ny = 6","60":"Water\nx = 1\ny = 7","61":"Water\nx = 1\ny = 10","62":"Water\nx = 1\ny = 11","63":"Water\nx = 2\ny = 3","64":"Water\nx = 2\ny = 4","65":"Water\nx = 2\ny = 5","66":"Water\nx = 2\ny = 6","67":"Water\nx = 2\ny = 7","68":"Water\nx = 2\ny = 10","69":"Water\nx = 2\ny = 11","70":"Water\nx = 3\ny = 0","71":"Water\nx = 3\ny = 1","72":"Water\nx = 3\ny = 2","73":"Water\nx = 3\ny = 3","74":"Water\nx = 3\ny = 4","75":"Water\nx = 3\ny = 5","76":"Water\nx = 3\ny = 6","77":"Water\nx = 3\ny = 7","78":"Water\nx = 3\ny = 8","79":"Water\nx = 3\ny = 9","80":"Water\nx = 3\ny = 10","81":"Water\nx = 3\ny = 11","82":"Water\nx = 3\ny = 12","83":"Water\nx = 3\ny = 13","84":"Water\nx = 3\ny = 14","85":"Water\nx = 4\ny = 0","86":"Water\nx = 4\ny = 1","87":"Water\nx = 4\ny = 2","88":"Water\nx = 4\ny = 3","89":"Water\nx = 4\ny = 4","90":"Water\nx = 4\ny = 5","91":"Water\nx = 4\ny = 9","92":"Water\nx = 4\ny = 10","93":"Water\nx = 4\ny = 11","94":"Water\nx = 4\ny = 12","95":"Water\nx = 4\ny = 13","96":"Water\nx = 4\ny = 14","97":"Water\nx = 5\ny = 0","98":"Water\nx = 5\ny = 1","99":"Water\nx = 5\ny = 2","100":"Water\nx = 5\ny = 3","101":"Water\nx = 5\ny = 4","102":"Water\nx = 5\ny = 5","103":"Water\nx = 5\ny = 9","104":"Water\nx = 5\ny = 10","105":"Water\nx = 5\ny = 11","106":"Water\nx = 5\ny = 12","107":"Water\nx = 5\ny = 13","108":"Water\nx = 5\ny = 14","109":"Water\nx = 6\ny = 0","110":"Water\nx = 6\ny = 1","111":"Water\nx = 6\ny = 2","112":"Water\nx = 6\ny = 3","113":"Water\nx = 6\ny = 4","114":"Water\nx = 6\ny = 5","115":"Water\nx = 6\ny = 6","116":"Water\nx = 6\ny = 7","117":"Water\nx = 6\ny = 8","118":"Water\nx = 6\ny = 9","119":"Water\nx = 6\ny = 10","120":"Water\nx = 6\ny = 11","121":"Water\nx = 6\ny = 12","122":"Water\nx = 6\ny = 13","123":"Water\nx = 6\ny = 14","124":"Water\nx = 7\ny = 0","125":"Water\nx = 7\ny = 1","126":"Water\nx = 7\ny = 2","127":"Water\nx = 7\ny = 3","128":"Water\nx = 7\ny = 4","129":"Water\nx = 7\ny = 5","130":"Water\nx = 7\ny = 6","131":"Water\nx = 7\ny = 7","132":"Water\nx = 7\ny = 8","133":"Water\nx = 7\ny = 9","134":"Water\nx = 7\ny = 10","135":"Water\nx = 7\ny = 11","136":"Water\nx = 7\ny = 12","137":"Water\nx = 7\ny = 13","138":"Water\nx = 7\ny = 14","139":"Water\nx = 8\ny = 0","140":"Water\nx = 8\ny = 1","141":"Water\nx = 8\ny = 2","142":"Water\nx = 8\ny = 3","143":"Water\nx = 8\ny = 4","144":"Water\nx = 8\ny = 5","145":"Water\nx = 8\ny = 6","146":"Water\nx = 8\ny = 7","147":"Water\nx = 8\ny = 8","148":"Water\nx = 8\ny = 9","149":"Water\nx = 8\ny = 10","150":"Water\nx = 8\ny = 11","151":"Water\nx = 8\ny = 12","152":"Water\nx = 8\ny = 13","153":"Water\nx = 8\ny = 14","154":"Water\nx = 9\ny = 0","155":"Water\nx = 9\ny = 1","156":"Water\nx = 9\ny = 2","157":"Water\nx = 9\ny = 3","158":"Water\nx = 9\ny = 4","159":"Water\nx = 9\ny = 5","160":"Water\nx = 9\ny = 6","161":"Water\nx = 9\ny = 7","162":"Water\nx = 9\ny = 8","163":"Water\nx = 9\ny = 9","164":"Water\nx = 9\ny = 10","165":"Water\nx = 9\ny = 11","166":"Water\nx = 10\ny = 0","167":"Water\nx = 10\ny = 1","168":"Water\nx = 10\ny = 2","169":"Water\nx = 10\ny = 5","170":"Water\nx = 10\ny = 6","171":"Water\nx = 10\ny = 7","172":"Water\nx = 10\ny = 8","173":"Water\nx = 10\ny = 9","174":"Water\nx = 10\ny = 10","175":"Water\nx = 10\ny = 11","176":"Water\nx = 11\ny = 0","177":"Water\nx = 11\ny = 1","178":"Water\nx = 11\ny = 2","179":"Water\nx = 11\ny = 5","180":"Water\nx = 11\ny = 6","181":"Water\nx = 11\ny = 7","182":"Water\nx = 11\ny = 8","183":"Water\nx = 11\ny = 9","184":"Water\nx = 11\ny = 10","185":"Water\nx = 11\ny = 11","186":"Water\nx = 11\ny = 12","187":"Water\nx = 11\ny = 13","188":"Water\nx = 11\ny = 14","189":"Water\nx = 12\ny = 0","190":"Water\nx = 12\ny = 1","191":"Water\nx = 12\ny = 2","192":"Water\nx = 12\ny = 5","193":"Water\nx = 12\ny = 6","194":"Water\nx = 12\ny = 7","195":"Water\nx = 12\ny = 8","196":"Water\nx = 12\ny = 9","197":"Water\nx = 12\ny = 10","198":"Water\nx = 12\ny = 11","199":"Water\nx = 12\ny = 14","200":"Water\nx = 13\ny = 0","201":"Water\nx = 13\ny = 1","202":"Water\nx = 13\ny = 2","203":"Water\nx = 13\ny = 3","204":"Water\nx = 13\ny = 4","205":"Water\nx = 13\ny = 5","206":"Water\nx = 13\ny = 6","207":"Water\nx = 13\ny = 7","208":"Water\nx = 13\ny = 8","209":"Water\nx = 13\ny = 9","210":"Water\nx = 13\ny = 10","211":"Water\nx = 13\ny = 11","212":"Water\nx = 13\ny = 14","213":"Water\nx = 14\ny = 0","214":"Water\nx = 14\ny = 1","215":"Water\nx = 14\ny = 2","216":"Water\nx = 14\ny = 3","217":"Water\nx = 14\ny = 4","218":"Water\nx = 14\ny = 5","219":"Water\nx = 14\ny = 6","220":"Water\nx = 14\ny = 7","221":"Water\nx = 14\ny = 8","222":"Water\nx = 14\ny = 9","223":"Water\nx = 14\ny = 10","224":"Water\nx = 14\ny = 11","225":"Water\nx = 14\ny = 12","226":"Water\nx = 14\ny = 13","227":"Water\nx = 14\ny = 14"}]}}
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[NEXT_PLAYER_INPUT] 16]
    15 15 0
    xxx............
    xxx............
    xxx............
    xx........xxx..
    ..........xxx..
    ...............
    ....xx.........
    ....xx.........
    .xx.xx.........
    .xx............
    ...............
    ...............
    xxx......xx.xx.
    xxx......xx.xx.
    xxx......xx....
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== Read from player
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 7 14
    
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== End Player
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[VIEW] 1]
    INTERMEDIATE_FRAME 1
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[NEXT_PLAYER_INPUT] 16]
    15 15 1
    xxx............
    xxx............
    xxx............
    xx........xxx..
    ..........xxx..
    ...............
    ....xx.........
    ....xx.........
    .xx.xx.........
    .xx............
    ...............
    ...............
    xxx......xx.xx.
    xxx......xx.xx.
    xxx......xx....
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== Read from player
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 7 14
    
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== End Player
    [2020/03/30 20:21:01][GF] INFO  : GameManager - Turn 2
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[VIEW] 2]
    KEY_FRAME 2
    {"duration":10,"entitymodule":"CS;G;S;G;R;R;T;C;C;C;T;C;C;C;T;C;C;C;C;C;C;T;C;C;C;C;C;C;T;C;C;C;C;T;C;C;C;C;T;C;C;C;T;C;C;C\nU289 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 105 y 910 c 0 R 20 W 5;260 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1870 y 550 c 0 R 20 W 5;263 0 f 15724527 ay 0.5 v 1 ff Arial ax 1 x 1890 y 740 s 30 T SILENCE fw bold z 5;251 0 sx 1 i sub.png sy 1.5 ay 0.5 v 1 ax 0.5 t -2;293 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1815 y 910 c 0 R 20 W 5;270 0 f 15724527 ay 0.5 v 1 ff Arial ax 0 x 30 y 740 s 30 T SILENCE fw bold z 5;276 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1595 y 790 c 0 R 20 W 5;271 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1870 y 790 c 0 R 20 W 5;268 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 270 y 790 c 0 R 20 W 5;282 0 f 15724527 ay 0.5 v 1 ff Arial ax 0 x 30 y 620 s 30 T SONAR fw bold z 5;286 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1705 y 670 c 0 R 20 W 5;265 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 105 y 790 c 0 R 20 W 5;287 0 f 15724527 ay 0.5 v 1 ff Arial ax 1 x 1890 y 860 s 30 T MINE fw bold z 5;250 0 x 960 y 960 v 1 ch 249 z 100;267 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 215 y 790 c 0 R 20 W 5;284 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1815 y 670 c 0 R 20 W 5;283 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1870 y 670 c 0 R 20 W 5;249 0 sx 1 i sub.png sy 1.5 ay 0.5 v 1 ax 0.5 t -1;288 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 50 y 910 c 0 R 20 W 5;285 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1760 y 670 c 0 R 20 W 5;290 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 160 y 910 c 0 R 20 W 5;269 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 325 y 790 c 0 R 20 W 5;277 0 f 15724527 ay 0.5 v 1 ff Arial ax 1 x 1890 y 620 s 30 T SONAR fw bold z 5;291 0 f 15724527 ay 0.5 v 1 ff Arial ax 0 x 30 y 860 s 30 T MINE fw bold z 5;274 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1705 y 790 c 0 R 20 W 5;258 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 160 y 550 c 0 R 20 W 5;272 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1815 y 790 c 0 R 20 W 5;279 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 105 y 670 c 0 R 20 W 5;262 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1760 y 550 c 0 R 20 W 5;266 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 160 y 790 c 0 R 20 W 5;280 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 160 y 670 c 0 R 20 W 5;261 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1815 y 550 c 0 R 20 W 5;264 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 50 y 790 c 0 R 20 W 5;294 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1760 y 910 c 0 R 20 W 5;256 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 50 y 550 c 0 R 20 W 5;273 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1760 y 790 c 0 R 20 W 5;257 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 105 y 550 c 0 R 20 W 5;252 0 x 960 y 960 v 1 ch 251 z 100;292 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1870 y 910 c 0 R 20 W 5;255 0 f 15724527 ay 0.5 v 1 ff Arial ax 1 x 1890 y 500 s 30 T TORPEDO fw bold z 5;281 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 215 y 670 c 0 R 20 W 5;259 0 f 15724527 ay 0.5 v 1 ff Arial ax 0 x 30 y 500 s 30 T TORPEDO fw bold z 5;275 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 1650 y 790 c 0 R 20 W 5;278 0 f 5592405 sx 0.5 sy 0.5 v 1 a 0.8 x 50 y 670 c 0 R 20 W 5;253 1 f -1 v 1 a 0.5 x 930 w 60 y 930 A 0 h 60 z 1;254 1 f -2 v 1 a 0.5 x 930 w 60 y 930 A 0 h 60 z 0","tooltips":[{"249":"Player 0","251":"Player 1"}]}
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[NEXT_PLAYER_INPUT] 3]
    7 14 6 6 3 4 6 3
    NA
    NA
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== Read from player
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - MINE N|TRIGGER 7 9
    
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== End Player
    [2020/03/30 20:21:01][GF] INFO  : GameManager - Turn 3
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[VIEW] 2]
    KEY_FRAME 3
    {"duration":500,"entitymodule":"CC\nU295 0 f -1 sx 0 sy 0 v 1 x 960 y 960 F 0 c -1 R 20;228 0 f -1 T 'MINE N > TRIGGER 7 9';295 0.2 sx 1 ~ sy 1 ~ F 0.5;250 0.5 sx 2 sy 2;250 1 sx 1 sy 1;235 1 f 5592405 sx 0.5 sy 0.5;295 1 sx 10 sy 10 a 0","tooltips":[{}]}
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[NEXT_PLAYER_INPUT] 3]
    7 14 6 5 3 4 6 3
    NA
    SURFACE 8
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== Read from player
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - MINE N|TRIGGER 7 9
    
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== End Player
    [2020/03/30 20:21:01][GF] INFO  : GameManager - Turn 4
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[VIEW] 2]
    KEY_FRAME 4
    {"entitymodule":"CC\nU228 0 f -2;296 0 f -2 sx 0 sy 0 v 1 x 960 y 960 F 0 c -2 R 20;296 0.2 sx 1 ~ sy 1 ~ F 0.5;252 0.5 sx 2 sy 2;252 1 sx 1 sy 1;245 1 f 5592405 sx 0.5 sy 0.5;296 1 sx 10 sy 10 a 0","tooltips":[{}]}
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[NEXT_PLAYER_INPUT] 3]
    7 14 5 5 3 4 6 3
    NA
    SURFACE 8
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== Read from player
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - MOVE N MSG|MSG WAZZAP 5?!
    
    [2020/03/30 20:21:01][GF] INFO  : GameRunner - 	=== End Player
    [2020/03/30 20:21:01][GF] INFO  : GameManager - End
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[VIEW] 2]
    KEY_FRAME 5
    {"entitymodule":"U228 0 f -1 T 'MOVE N MSG';229 0 T 'WAZZAP 5?!';233 1 f 5592405 sx 0.5 sy 0.5;234 1 f 5592405 sx 0.5 sy 0.5;232 1 f 5592405 sx 0.5 sy 0.5;231 1 f 5592405 sx 0.5 sy 0.5;230 1 f 5592405 sx 0.5 sy 0.5","tooltips":[{}],"endScreen":[[-1,5],"logo.png",["-1 life","5 lives"]]}
    [2020/03/30 20:21:01][GF] INFO  : GameManager - [[UINPUT] 1]
    [2020/03/30 20:21:01][GF] INFO  : GameManager - seed=1337
    http://localhost:8888/test.html
    Exposed web server dir: /private/var/folders/9_/4l2_p7_108z0_p050d4ngn8w0000gn/T/codingame
    [2020/03/30 20:21:02][GF] INFO  : xnio - XNIO version 3.3.8.Final
    [2020/03/30 20:21:02][GF] INFO  : nio - XNIO NIO Implementation Version 3.3.8.Final
```
</details>
