<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        得点管理システム
    </c:param>


    <c:param name="scripts">

        <style>

            .grmr-search-box {
                margin-left: 1rem;
                margin-right: 1rem;
                padding: 0.35rem 1rem 0.45rem 1rem;
            }

            .grmr-subject-row {
                display: grid;
                grid-template-columns: 10rem 8rem 8rem 18rem 4rem;
                column-gap: 0.9rem;
                align-items: center;
            }

            .grmr-student-row {
                display: grid;
                grid-template-columns: 10rem 19rem 4rem;
                column-gap: 0.9rem;
                align-items: center;
            }

            .grmr-left-label {
                margin-left: 1rem;
                padding-bottom: 0.25rem;
            }

            .grmr-item-label {
                margin-top: 0.3rem;
                margin-bottom: 0.3rem;
            }

            .grmr-select-year,
            .grmr-select-class {
                width: 7rem;
            }

            .grmr-select-subject {
                width: 15rem;
            }

            .grmr-student-no {
                width: 16rem;
            }

            .grmr-search-btn {
                width: 3.5rem;
            }

            .grmr-separator {
                margin-top: 0.5rem;
                margin-bottom: 0.5rem;
            }

        </style>

    </c:param>


    <c:param name="content">

        <section class="me-4">

            <h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
                成績一覧（科目）
            </h2>


            <div class="mx-3 border rounded grmr-search-box">

                <!-- 科目検索 -->
                <form action="TestListSubjectExecute.action"
                      method="post">

                    <div class="grmr-subject-row">

                        <div class="grmr-left-label">
                            科目情報
                        </div>


                        <div>

                            <label class="form-label grmr-item-label">
                                入学年度
                            </label>

                            <select
                                class="form-select form-select-sm grmr-select-year"
                                name="f1">

                                <option value="0">
                                    --------
                                </option>

                                <c:forEach var="year"
                                             items="${ent_year_set}">

                                    <option value="${year}"
                                        <c:if test="${year == f1}">
                                            selected
                                        </c:if>>

                                        ${year}

                                    </option>

                                </c:forEach>

                            </select>

                        </div>


                        <div>

                            <label class="form-label grmr-item-label">
                                クラス
                            </label>

                            <select
                                class="form-select form-select-sm grmr-select-class"
                                name="f2">

                                <option value="0">
                                    --------
                                </option>

                                <c:forEach var="num"
                                             items="${class_num_set}">

                                    <option value="${num}"
                                        <c:if test="${num == f2}">
                                            selected
                                        </c:if>>

                                        ${num}

                                    </option>

                                </c:forEach>

                            </select>

                        </div>


                        <div>

                            <label class="form-label grmr-item-label">
                                科目
                            </label>

                            <select
                                class="form-select form-select-sm grmr-select-subject"
                                name="f3">

                                <option value="0">
                                    --------
                                </option>

                                <c:forEach var="sub"
                                             items="${subject_set}">

                                    <option value="${sub.cd}"
                                        <c:if test="${sub.cd == f3}">
                                            selected
                                        </c:if>>

                                        ${sub.name}

                                    </option>

                                </c:forEach>

                            </select>

                        </div>


                        <div>

                            <button type="submit"
                                    class="btn btn-secondary btn-sm grmr-search-btn">

                                検索

                            </button>

                        </div>

                    </div>

                </form>


                <hr class="grmr-separator">


                <!-- 学生検索 -->
                <form action="TestListStudentExecute.action"
                      method="post">

                    <div class="grmr-student-row">

                        <div class="grmr-left-label">
                            学生情報
                        </div>


                        <div>

                            <label class="form-label grmr-item-label">
                                学生番号
                            </label>

                            <input type="text"
                                   class="form-control form-control-sm grmr-student-no"
                                   name="f4"
                                   value="${f4}"
                                   placeholder="学生番号を入力してください">

                        </div>


                        <div>

                            <button type="submit"
                                    class="btn btn-secondary btn-sm grmr-search-btn">

                                検索

                            </button>

                        </div>

                    </div>

                </form>

            </div>


            <c:if test="${not empty subject}">
                <div class="mx-3 mt-3 mb-2">
                    科目：${subject.name}
                </div>
            </c:if>


            <c:if test="${not empty message}">
                <div class="mx-3">
                    ${message}
                </div>
            </c:if>


            <c:if test="${students.size() > 0}">

                <div class="mx-3 mt-3"
                     style="width:86%;">

                    <table class="table table-sm">

                        <thead>

                            <tr>

                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>

                                <c:forEach begin="1"
                                             end="2"
                                             var="num">

                                    <th class="text-center">

                                        ${num}回

                                    </th>

                                </c:forEach>

                            </tr>

                        </thead>


                        <tbody>

                            <c:forEach var="student"
                                         items="${students}">

                                <tr>

                                    <td>
                                        ${student.entYear}
                                    </td>

                                    <td>
                                        ${student.classNum}
                                    </td>

                                    <td>
                                        ${student.studentNo}
                                    </td>

                                    <td>
                                        ${student.studentName}
                                    </td>


                                    <c:forEach begin="1"
                                                 end="2"
                                                 var="num">

                                        <td class="text-center">

                                            <c:choose>

                                                <c:when test="${empty student.points[num]}">

                                                    -

                                                </c:when>

                                                <c:otherwise>

                                                    ${student.points[num]}

                                                </c:otherwise>

                                            </c:choose>

                                        </td>

                                    </c:forEach>

                                </tr>

                            </c:forEach>

                        </tbody>

                    </table>

                </div>

            </c:if>

        </section>

    </c:param>

</c:import>
