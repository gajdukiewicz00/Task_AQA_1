# Triangle Classification — AQA Task

A Java console application that reads three side lengths, determines whether they form a triangle, and classifies it using an enum `TriangleTypeEnum`.  
The project includes full domain analysis, boundary testing, and parametrized JUnit tests.

---

## 📁 Project Structure

```
src
├── main
│   └── java
│       └── com.example.triangles
│           ├── Main.java
│           ├── TriangleClassifier.java
│           └── TriangleTypeEnum.java
└── test
    └── java
        └── com.example.triangles
            └── TriangleClassifierTest.java
```

---

## ▶️ How It Works

1. **Input**: three values (as `long`)
2. Program validates whether the values can form a triangle  
   - If **not a triangle** → prints:  
     ```
     NOT_TRIANGLE
     ```
3. If valid → classification is printed via `TriangleTypeEnum`:
   - `EQUILATERAL`
   - `ISOSCELES`
   - `SCALENE`

---

## 🏗 Build

```bash
mvn clean package
```

## ▶️ Run

```bash
echo "3 4 5" | java -jar target/TriangleClassifier.jar
```

## 🧪 Run Tests

```bash
mvn test
```

---

## 📘 DOMAIN TESTING

This section contains all equivalence classes and boundary values used to validate and classify triangles.

### 1. Invalid Input & Non-Triangles (isTriangle == false)

| ID  | Class Description              | Condition           | Examples                                    | Expected Result  |
|-----|--------------------------------|---------------------|---------------------------------------------|------------------|
| NV1 | Zero sides                     | any side = 0        | (0,1,1), (0,0,0)                            | NOT_TRIANGLE     |
| NV2 | Negative sides                 | any side < 0        | (-1,2,2), (-5,3,3)                          | NOT_TRIANGLE     |
| NV3 | Triangle inequality violated   | min + mid ≤ max     | (1,2,3), (5,5,10), (1,1000000,1000001)      | NOT_TRIANGLE     |
| NV4 | Invalid numeric input          | invalid string or overflow during parsing | ("x","1","1"), ("1","2","9e18") | INVALID_INPUT    |

### 2. Valid Triangles (isTriangle == true)

| ID | Triangle Type | Condition                  | Examples                                                                                      | Expected Result |
|----|---------------|----------------------------|-----------------------------------------------------------------------------------------------|-----------------|
| V1 | Equilateral   | a = b = c                  | (1,1,1), (2147483647,2147483647,2147483647)                                                   | EQUILATERAL     |
| V2 | Isosceles     | exactly two sides equal    | MIN: (2,2,3)<br>(5,5,8), (10,10,19), (1500000000,1500000000,2000000000)                      | ISOSCELES       |
| V3 | Scalene       | all sides different        | (3,4,5), (2,3,4), (6,7,10)                                                                    | SCALENE         |

#### 🔹 Special note on V2 (Isosceles MIN set)
The reviewer requested explicitly adding the smallest positive integer example:

**(2, 2, 3)**

Since:
- 2 + 2 > 3

This is the minimal valid representative for the isosceles class.

---

## 🧪 Test Strategy

The test suite includes:

### ✔ Combined Domain Test
A single parametrized test verifies:
- `isTriangle(a,b,c)`
- `classify(a,b,c)` consistency
- `Optional.empty()` for invalid triangles
- Correct `TriangleTypeEnum` for valid triangles

*This addresses the reviewer comment about redundancy in `testIsTriangle` and `testClassifyInvalid`.*

### ✔ Permutation Invariance
A separate test confirms that order of sides does not affect classification.

### ✔ Parsing Tests (parseThreeInts)
Covers:
- Valid numeric strings
- Invalid numeric strings
- Overflow errors
- Null inputs (NPE expected)

---

## 🧩 Technologies

- Java 17
- Maven
- JUnit 5
- Parametrized Tests
- Enum-based classification (`TriangleTypeEnum`)

---

## 📜 Example Output

**Input:**
```
3 4 5
```

**Output:**
```
SCALENE
```
