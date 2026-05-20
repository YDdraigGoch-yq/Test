<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>


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
                成績一覧（学生）
            </h2>


            <div class="mx-3 border rounded grmr-search-box">

                <%-- 科目検索 --%>
                <form action="TestListSubjectExecute.action"
                      method="post">

                    <input type="hidden"
                           name="f"
                           value="sj">

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

                                <c:forEach var="subject"
                                             items="${subject_set}">

                                    <option value="${subject.cd}"
                                        <c:if test="${subject.cd == f3}">
                                            selected
                                        </c:if>>

                                        ${subject.name}

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


                <%-- 学生検索 --%>
                <form action="TestListStudentExecute.action"
                      method="post">

                    <input type="hidden"
                           name="f"
                           value="st">

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
                                   placeholder="学生番号を入力してください"
                                   required>

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



            <c:if test="${not empty student}">
                <div class="mx-3 mt-3 mb-2 fw-bold">

                    氏名：
                    ${student.name}
                    （${student.no}）

                </div>
            </c:if>



            <c:if test="${not empty message}">
                <div class="mx-3 fw-bold">

                    ${message}

                </div>
            </c:if>



            <c:if test="${scores.size() > 0}">

                <form action="TestBatch.action"
                      method="post">

                    <div class="mx-3 mt-3">

                        <table class="table table-sm">

                            <thead>

                                <tr>

                                    <c:if test="${mode=='delete'}">
                                        <th>選択</th>
                                    </c:if>

                                    <th>科目名</th>
                                    <th>科目コード</th>
                                    <th>回数</th>
                                    <th>点数</th>

                                </tr>

                            </thead>


                            <tbody>

                                <c:forEach var="score"
                                             items="${scores}">

                                    <tr>

                                        <c:if test="${mode=='delete'}">

                                            <td>

                                                <input type="checkbox"
                                                    name="selected"
                                                    value="${student.no},
                                                    ${score.subjectCd},
                                                    ${score.num}">

                                            </td>

                                        </c:if>


                                        <td>${score.subjectName}</td>

                                        <td>${score.subjectCd}</td>

                                        <td>${score.num}</td>


                                        <td>

                                            <c:choose>

                                                <c:when test="${mode=='edit'}">

                                                    <input type="text"
                                                        name="point"
                                                        value="${score.point}"
                                                        style="width:70px;">

                                                    <input type="hidden"
                                                        name="target"
                                                        value="${student.no},
                                                        ${score.subjectCd},
                                                        ${score.num}">

                                                </c:when>


                                                <c:otherwise>

                                                    ${score.point}

                                                </c:otherwise>

                                            </c:choose>

                                        </td>

                                    </tr>

                                </c:forEach>

                            </tbody>

                        </table>

                    </div>


                    <c:if test="${mode=='edit'}">

                        <button
                            name="actionType"
                            value="update"
                            class="btn btn-primary mx-3">

                            変更を保存

                        </button>

                    </c:if>



                    <c:if test="${mode=='delete'}">

                        <button
                            name="actionType"
                            value="delete"
                            class="btn btn-danger mx-3"
                            onclick="return confirm('選択した成績を削除しますか？');">

                            選択した成績を削除

                        </button>

                    </c:if>


                    <input type="hidden"
                           name="mode"
                           value="${mode}">

                    <input type="hidden"
                           name="f"
                           value="st">

                    <input type="hidden"
                           name="studentNo"
                           value="${student.no}">

                </form>

            </c:if>

        </section>

    </c:param>

</c:import>
